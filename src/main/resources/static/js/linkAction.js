window.onload = function (e) {
    liff.init(function (data) {
        liff.openWindow({
            url: "https://botchan.page.link/?" +
            "link=https://sheltered-scrubland-23764.herokuapp.com/link_start" +
            "&apn=net.onlybiz.botchanclient" +
            "&isi=123456789&ibi=net.onlybiz.botchanClient" +
            "userId=aaaaa",
            external: true
        });
    });
};