(function (window, angular, undefined) {
    'use strict';

    angular
        .module('spotify', [])
        .provider('Spotify', function () {

            // Module global settings.
            var settings = {};
            settings.clientId = null;
            settings.redirectUri = null;
            settings.scope = null;
            settings.authToken = null;

            this.setClientId = function (clientId) {
                settings.clientId = clientId;
                return settings.clientId;
            };

            this.getClientId = function () {
                return settings.clientId;
            };

            this.setAuthToken = function (authToken) {
                settings.authToken = authToken;
                return settings.authToken;
            };

            this.setRedirectUri = function (redirectUri) {
                settings.redirectUri = redirectUri;
                return settings.redirectUri;
            };

            this.getRedirectUri = function () {
                return settings.redirectUri;
            };

            this.setScope = function (scope) {
                settings.scope = scope;
                return settings.scope;
            };

            var utils = {};
            utils.toQueryString = function (obj) {
                var parts = [];
                angular.forEach(obj, function (value, key) {
                    this.push(encodeURIComponent(key) + '=' + encodeURIComponent(value));
                }, parts);
                return parts.join('&');
            };

            /**
             * API Base URL
             */
            settings.apiBase = 'https://api.spotify.com/v1';

            this.$get = ['$q', '$http', '$window', function ($q, $http, $window) {

                function NgSpotify () {
                    this.clientId = settings.clientId;
                    this.redirectUri = settings.redirectUri;
                    this.apiBase = settings.apiBase;
                    this.scope = settings.scope;
                    this.authToken = settings.authToken;
                    this.toQueryString = utils.toQueryString;
                }

                function openDialog (uri, name, options, cb) {
                    var win = window.open(uri, name, options);
                    var interval = window.setInterval(function () {
                        try {
                            if (!win || win.closed) {
                                window.clearInterval(interval);
                                cb(win);
                            }
                        } catch (e) {}
                    }, 1000);
                    return win;
                }

                NgSpotify.prototype = {

                    getTopTracks: function (country){
                            let data = "{\"country\" :\"";
                            data = data.concat(country.concat("\"}"));
                            let url = " http://localhost:8080/track";
                            console.log("DATA : ".concat(data));
                            $http.put(url, JSON.stringify(data)).then(function (response) {
                                console.log(url);
                                if(response.data)
                                    console.log("Request sent successfully!");
                            })

                                .catch(function (err) {
                                    console.log(err);
                                    alert("Error Occured!");
                                    throw err;
                                });

                        },

                    setAuthToken: function (authToken) {
                        this.authToken = authToken;
                        return this.authToken;
                    },

                    login: function () {
                        var deferred = $q.defer();
                        var that = this;

                        var w = 400,
                            h = 500,
                            left = (screen.width / 2) - (w / 2),
                            top = (screen.height / 2) - (h / 2);

                        var params = {
                            client_id: this.clientId,
                            redirect_uri: this.redirectUri,
                            scope: this.scope || '',
                            response_type: 'token'
                        };

                        var authCompleted = false;
                        var authWindow = openDialog(
                            'https://accounts.spotify.com/authorize?' + this.toQueryString(params),
                            'Spotify',
                            'menubar=no,location=no,resizable=yes,scrollbars=yes,status=no,width=' + w + ',height=' + h + ',top=' + top + ',left=' + left,
                            function () {
                                if (!authCompleted) {
                                    deferred.reject();
                                }
                            }
                        );

                        function storageChanged (e) {
                            if (e.key === 'spotify-token') {
                                if (authWindow) { authWindow.close(); }
                                authCompleted = true;

                                that.setAuthToken(e.newValue);
                                $window.removeEventListener('storage', storageChanged, false);

                                deferred.resolve(e.newValue);
                            }
                        }

                        $window.addEventListener('storage', storageChanged, false);

                        return deferred.promise;
                    }
                };

                return new NgSpotify();
            }];

        });

}(window, angular));