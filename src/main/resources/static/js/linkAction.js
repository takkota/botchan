window.onload = function (e) {
    liff.init(function (data) {
        liff.openWindow({
            url: "https//botchan.page.link/link_start" +
            "?link=https%3A%2F%2Fsheltered-scrubland-23764.herokuapp.com%2Flink_start%3FuserId%3Daaa" +
            "&apn=net.onlybiz.botchanclient" +
            "&ibi=net.onlybiz.botchanClient" +
            "&isi=123456789" +
            "&userId=bbbb",
            //url: "https://botchan.page.link/link_start?" +
            //"link=https://sheltered-scrubland-23764.herokuapp.com/link_start?userId=aaaa" +
            //"&apn=net.onlybiz.botchanclient" +
            //"&isi=123456789&ibi=net.onlybiz.botchanClient",
            external: true
        });
    });
};
