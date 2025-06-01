function ToBlank() {
    var links = document.querySelectorAll("a:not([target=_blank])");
    for (var i = 0; i < links.length; i++) {
        links[i].target = "_blank";
    }
    return links.length > 0
}
function ToBlankLoop() {
    if (ToBlank()) {
        window.addEventListener("scroll", ToBlank);
        return;
    }
    setTimeout("ToBlankLoop()", 100);
}
ToBlankLoop();

var n = 0;
function ClickBut(query) {
    var but = document.querySelector(query);
    console.log(query + ": " + but);
    if (but != null) {
        but.click();
        return;
    }
    if (n++ > 10) {
        return;
    }
    setTimeout('ClickBut(".ytp-unmute")', 500);
}
ClickBut(".ytp-unmute");  /*取消静音*/

var QUALITIES =  ['auto', 'tiny', 'small', 'medium', 'large', 'hd720', 'hd1080', 'hd1440', 'hd2160', 'hd2880', 'highres'];
var movie_player = document.getElementById("movie_player");
/*console.log(movie_player.getPlaybackQuality());*/
if (movie_player != null && movie_player.zz_set == null) {
    movie_player.setPlaybackQualityRange("hd720");
    movie_player.zz_set = true;
}

function PauseOrPlay() {
    if (movie_player.getPlayerState() == 1) {
        movie_player.pauseVideo();
    } else {
        movie_player.playVideo();
    }
}
