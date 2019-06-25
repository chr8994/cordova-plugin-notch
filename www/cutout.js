var exec = require('cordova/exec');

exports.adjust = function () {
    return new Promise((resolve, reject) => {
        exec((statusBarHeight) => {
            resolve(statusBarHeight);
        }, reject, 'Cutout', 'getStatusBarHeight');
    })
};
