window.onload = function (e) {
    liff.init(function (data) {
        initializeApp(data);
        liff.openWindow({
            url: "http://botchan.page.link/link_start?userId=b",
            external: true
        });
    });
};

function initializeApp(data) {
    //document.getElementById('languagefield').textContent = data.language;
    //document.getElementById('viewtypefield').textContent = data.context.viewType;
    document.getElementById('useridfield').textContent = data.context.userId;
    //document.getElementById('utouidfield').textContent = data.context.utouId;
    //document.getElementById('roomidfield').textContent = data.context.roomId;
    //document.getElementById('groupidfield').textContent = data.context.groupId;

    // openWindow call
//    document.getElementById('openwindowbutton').addEventListener('click', function () {
//        liff.openWindow({
//            url: 'https://line.me'
//        });
//    });
//
//    // closeWindow call
//    document.getElementById('closewindowbutton').addEventListener('click', function () {
//        liff.closeWindow();
//    });
//
//    // sendMessages call
//    document.getElementById('sendmessagebutton').addEventListener('click', function () {
//        liff.sendMessages([{
//            type: 'text',
//            text: "You've successfully sent a message! Hooray!"
//        }, {
//            type: 'sticker',
//            packageId: '2',
//            stickerId: '144'
//        }]).then(function () {
//            window.alert("Message sent");
//        }).catch(function (error) {
//            window.alert("Error sending message: " + error);
//        });
//    });
//
//    //get profile call
//    document.getElementById('getprofilebutton').addEventListener('click', function () {
//        liff.getProfile().then(function (profile) {
//            document.getElementById('useridprofilefield').textContent = profile.userId;
//            document.getElementById('displaynamefield').textContent = profile.displayName;
//
//            var profilePictureDiv = document.getElementById('profilepicturediv');
//            if (profilePictureDiv.firstElementChild) {
//                profilePictureDiv.removeChild(profilePictureDiv.firstElementChild);
//            }
//            var img = document.createElement('img');
//            img.src = profile.pictureUrl;
//            img.alt = "Profile Picture";
//            profilePictureDiv.appendChild(img);
//
//            document.getElementById('statusmessagefield').textContent = profile.statusMessage;
//            toggleProfileData();
//        }).catch(function (error) {
//            window.alert("Error getting profile: " + error);
//        });
//    });
}
