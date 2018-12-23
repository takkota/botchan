window.onload = function (e) {
    liff.init(function (data) {
        liff.openWindow({
            url: url,
            external: true
        });
    });
};
