window.onload = function (e) {
    liff.init(function (data) {
        liff.openWindow({
            url: "https://botchan.page.link/" +
            "?ibi=net.onlybiz.botchanClient" +
            "&isi=123456789&apn=net.onlybiz.botchanclient" +
            "&link=https%3A%2F%2Fsheltered-scrubland-23764.herokuapp.com%3FuserId%3Dabc",
            external: true
        });
    });
};
