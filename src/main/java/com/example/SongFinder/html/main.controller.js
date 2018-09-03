'use strict';
let app = angular
    .module('exampleApp', ['spotify'])
    .config(function (SpotifyProvider) {
        SpotifyProvider.setClientId('1a0a4f93cf484f8a8ab1ce187023bdc3');
        SpotifyProvider.setRedirectUri('http://localhost:63342/SongFinder/com/example/SongFinder/html/callback.html');
        SpotifyProvider.setScope('playlist-read-private playlist-modify-public \
                                playlist-modify-private playlist-read-collaborative');
    })
    .config(['$qProvider', function($qProvider){
        $qProvider.errorOnUnhandledRejections(false);
    }])

    .controller('MainController', ['$scope', 'Spotify', function ($scope, Spotify) {
        $scope.getTopTracks = function (country) {
            alert(country);
            Spotify.getTopTracks(country, $scope.accesssToken);
        };

        $scope.login = function () {
            Spotify.login().then(function (data) {
                console.log(data);

                $scope.usa = "united states";
                $scope.canada = "canada";
                $scope.mexico = "mexico";
                $scope.guatemala = "guatemala";
                $scope.honduras = "honduras";
                $scope.nicaragua = "nicaragua";
                $scope.costa_rica = "costa rica";
                $scope.panama = "panama";
                $scope.ecuador = "ecuador";
                $scope.colombia = "colombia";
                $scope.venezuela = "venezuela";
                $scope.guyana = "guyana";
                $scope.suriname = "suriname";
                $scope.brazil = "brazil";
                $scope.paraguay = "paraguay";
                $scope.peru = "peru";
                $scope.bolivia = "bolivia";
                $scope.chile = "chile";
                $scope.argentina = "argentina";
                $scope.uruguay = "uruguay";
                $scope.cuba = "cuba";
                $scope.haiti = "haiti";
                $scope.norway = "norway";
                $scope.sweden = "sweden";
                $scope.finland = "finland";
                $scope.estonia = "estonia";
                $scope.latvia = "latvia";
                $scope.lithuania = "lithuania";
                $scope.belarus = "belarus";
                $scope.ukraine = "ukraine";
                $scope.poland = "poland";
                $scope.germany = "germany";
                $scope.denmark = "denmark";
                $scope.united_kingdom = "united kingdom";
                $scope.ireland = "ireland";
                $scope.france = "france";
                $scope.spain = "spain";
                $scope.portugal = "portugal";
                $scope.italy = "italy";
                $scope.romania = "romania";
                $scope.greece = "greece";
                $scope.bulgaria = "bulgaria";
                $scope.turkey = "turkey";
                $scope.turkey = "turkey";
                $scope.russia = "russia";
                $scope.georgia = "georgia";
                $scope.kazakhstan = "kazakhstan";
                $scope.mongolia = "mongolia";
                $scope.china = "china";
                $scope.kyrgyzstan = "kyrgyzstan";
                $scope.tajikistan = "tajikistan";
                $scope.uzbekistan = "uzbekistan";
                $scope.turkmenistan = "turkmenistan";
                $scope.azerbaijan = "azerbaijan";
                $scope.iran = "iran";
                $scope.afghanistan = "afghanistan";
                $scope.pakistan = "pakistan";
                $scope.india = "india";
                $scope.nepal = "nepal";
                $scope.vietnam = "vietnam";
                $scope.thailand = "thailand";
                $scope.north_korea = "north korea";
                $scope.south_korea = "south korea";
                $scope.japan = "japan";
                $scope.taiwan = "taiwan";
                $scope.malaysia = "malaysia";
                $scope.australia = "australia";
                $scope.new_zealand = "new zealand";
                $scope.syria = "syria";
                $scope.iraq = "iraq";
                $scope.lebanon = "lebanon";
                $scope.kuwait = "kuwait";
                $scope.saudi_arabia = "saudi arabia";
                $scope.iceland = "iceland";
                $scope.netherlands = "netherlands";
                $scope.tunisia = "tunisia";
                $scope.israel = "israel";
                $scope.egypt = "egypt";

                $scope.accesssToken = data;
                $scope.editmode = true;
                alert("You are now logged in");
            }, function () {
                $scope.editmode = false;
                console.log('didn\'t log in');
            });
        };
    }]);

