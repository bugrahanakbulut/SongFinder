'use strict';
let spotify_app = angular
    .module('exampleApp', ['spotify']);
spotify_app.config(function (SpotifyProvider) {
    SpotifyProvider.setClientId('1a0a4f93cf484f8a8ab1ce187023bdc3');
    SpotifyProvider.setRedirectUri('http://localhost:63342/SongFinder/com/example/SongFinder/html/callback.html');
    SpotifyProvider.setScope('playlist-read-private playlist-modify-public \
                                playlist-modify-private playlist-read-collaborative');
});

spotify_app.config(['$qProvider', function($qProvider){
        $qProvider.errorOnUnhandledRejections(false);
}]);

spotify_app.controller('spotifycontroller', ['$scope', 'Spotify', function ($scope, Spotify){
    $scope.login = function () {
        Spotify.login().then(function (data) {
            console.log('LOGIN OUTPUT : '.concat(data));
            $scope.accesssToken = data;
            $scope.editmode = true;
            console.log("You are now logged in");
        }, function (errmsg) {
            $scope.editmode = false;
            console.log('didn\'t log in');
        });
    };

    $scope.getTopTracks = function (country) {
        console.log($scope.country);
        Spotify.getTopTracks(country, $scope.accesssToken);
    }

}]);

