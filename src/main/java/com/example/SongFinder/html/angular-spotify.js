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
                    getUserId: function(autKey){
                        let deferred = $q.defer();
                        let apiBase = "https://api.spotify.com/v1/me";
                        $http({method: "GET", url: apiBase, headers:{
                                "Accept": "application/json",
                                "Content-Type" : "application/json",
                                "Authorization" : "Bearer ".concat(autKey)
                            }
                        })
                            .then(function (response) {
                                deferred.resolve(response.data.id);
                        }
                            ,function (error) {
                                deferred.resolve(error.data);
                            });
                        return deferred.promise;
                    },

                    createPlayList: function(userProm, autKey, country){
                        let deferred = $q.defer();
                        userProm.then(function (id){
                                let url = "https://api.spotify.com/v1/users/";
                                url = url.concat(id);
                                url = url.concat("/playlists");
                                $http({method: "POST", url: url, headers:{
                                        "Accept": "application/json",
                                        "Content-Type" : "application/json",
                                        "Authorization" : "Bearer ".concat(autKey)
                                    }, data: {
                                        name: "Top Tracks of ".concat(country),
                                        description: "Top Tracks of ".concat(country),
                                        public: false
                                    }
                                }).then(function (response){
                                    console.log(response);
                                    deferred.resolve(response.data)
                                }, function (error){
                                    console.log(error);
                                    deferred.resolve(error);
                                });
                            });
                        return deferred.promise;
                    },

                    getTopTracks: function (country, autKey){

                        let loaderStyle = "#loader {\n" +
                            "    position: fixed;\n" +
                            "    top: 1000px;\n" +
                            "    left: 100px;" +
                            "    border: 16px solid #f3f3f3; /* Light grey */\n" +
                            "    border-top: 16px solid #3498db; /* Blue */\n" +
                            "    border-radius: 50%;\n" +
                            "    width: 50px;\n" +
                            "    height: 50px;\n" +
                            "    animation: spin 2s linear infinite;\n" +
                            "}\n" +
                            "\n" +
                            "@keyframes spin {\n" +
                            "    0% { transform: rotate(0deg); }\n" +
                            "    100% { transform: rotate(360deg); }\n" +
                            "}";

                        let loaderHTML = "<div id=\"loader\"></div>";
                        document.getElementById("body").innerHTML = document.getElementById("body").innerHTML.concat(loaderHTML);21
                        document.getElementById("style").innerHTML = loaderStyle;



                        let userID = this.getUserId(autKey);
                        let playListID = this.createPlayList(userID, autKey, country);

                        let url = " http://localhost:8080/track";
                        let body = {
                            country: country,
                            autKey: autKey
                        };

                        $http.put(url, body).then(function (response) {
                            // console.log(url);
                            if(response.data){
                                console.log(response.data);
                                let i;
                                let uris = "";
                                for(i = 0; i < response.data.length; i++){
                                    let tmp = response.data[i];
                                    uris = uris.concat(tmp.spotifyUri);
                                    if(i !== 9)
                                        uris = uris.concat(",");
                                }
                                playListID.then(function (playlist) {
                                    let url = "https://api.spotify.com/v1/playlists/";
                                    url = url.concat(playlist.id);
                                    url = url.concat("/tracks?uris=");
                                    url = url.concat(uris);
                                    $http({method: "POST", url: url, headers:{
                                            "Accept": "application/json",
                                            "Content-Type" : "application/json",
                                            "Authorization" : "Bearer ".concat(autKey)
                                        }
                                    }).then(function (response) {
                                        console.log(response)
                                    }, function (error) {
                                        console.log(error)
                                    });

                                    let playListURI = playlist.uri;
                                    let splitted = playListURI.split(":");
                                    let output = "";
                                    output = output.concat("<iframe id=\"playerFrame\" src=\"https://open.spotify.com/embed/user/")
                                    let i = 2;
                                    for(i; i <splitted.length; i++){
                                        output = output.concat(splitted[i]);
                                        if(i !== splitted.length-1)
                                            output = output.concat("/");
                                    }
                                    let body = document.getElementById("body").innerHTML;
                                    output = output.concat("\" width=\"300\" height=\"380\" frameborder=\"0\" allowtransparency=\"true\" allow=\"encrypted-media\"></iframe>")

                                    let styleHTML = "#playerFrame{\n" +
                                        "    position: absolute;\n" +
                                        "    top: 1000px;\n" +
                                        "    left: 25px;\n" +
                                        "}\n";

                                    document.getElementById("body").innerHTML = body.concat(output);
                                    document.getElementById("style").innerHTML = (styleHTML);
                                    // console.log(document.getElementById("body").innerHTML)


                                    // console.log(output);
                                    // document.write(output);
                                });
                                console.log("Request sent successfully!");
                            }
                        })

                                .catch(function (err) {
                                    console.log(err);
                                    document.getElementById("style").innerHTML = "";
                                    alert("Error Occurred!");
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