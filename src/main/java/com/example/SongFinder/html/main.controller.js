let app = angular
    .module('exampleApp', ['spotify'])
    .config(function (SpotifyProvider) {
        SpotifyProvider.setClientId('1a0a4f93cf484f8a8ab1ce187023bdc3');
        SpotifyProvider.setRedirectUri('http://localhost:63342/SongFinder/com/example/SongFinder/html/callback.html');
        SpotifyProvider.setScope('playlist-read-private');
    })
    .config(['$qProvider', function($qProvider){
        $qProvider.errorOnUnhandledRejections(false);
    }])

    .controller('MainController', ['$scope', 'Spotify', function ($scope, Spotify) {

        $scope.getTopTracks = function (country) {
            Spotify.getTopTracks(country);
        };

        $scope.login = function () {
            Spotify.login().then(function (data) {
                console.log(data);
                $scope.editmode = true;
                alert("You are now logged in");
            }, function () {
                $scope.editmode = false;
                console.log('didn\'t log in');
            });
        };
    }]);

