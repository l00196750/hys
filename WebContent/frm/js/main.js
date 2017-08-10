// define([ 'frm/portal/Portal' ], function(app) {

//     portal.callService("GetLocalInfo").then(function(data){
//         var deferred = $.Deferred();
//         portal.appGlobal.set(fish.pick(data, "charset", "version","language", "shortLanguage"));

//         fish.setLanguage(portal.appGlobal.get('shortLanguage'));

//         if (fish.cookies.get("skin")) {
//             $("#indexCss").prop("href", fish.cookies.get("skin"));
//         }

//         portal.appGlobal.set("commoni18n", {});//框架公用的
//         portal.appGlobal.set("customi18n", {});//客户定制的

//         var i18nArr = ["i18n/common." + data.language];
//         if(data.CUSTOM_LANGUAGE){
//             i18nArr.push("i18n/custom." + data.CUSTOM_LANGUAGE);
//         }
//         require(i18nArr, function(commonI18n,customI18n) {
//             portal.appGlobal.get("commoni18n")[data.language] = commonI18n;
//             if (customI18n) {
//                 var argu = _.omit(customI18n,function(value, key, object) {
//                     return !/[^\s]+/.test(value);
//                 });
//                 portal.appGlobal.get("customi18n")[data.language] = argu;
//             }
//             deferred.resolve();
//         })
//         portal.appGlobal.set("ssoMode", data.SSO_MODE);
//         return deferred.promise();
//     }).then(function(){
//         require([ 'modules/index/views/IndexView' ], function(IndexView) {
//             new IndexView({
//                 el : $('#app')
//             }).index();
//         });
//     })

// });
//

define(function(){
    // 加载lib
    console.log('main');
    window.Wyl = {};

    require(['backbone', 'bootstrap', 'underscore', 'jsGrid', 'jsGridLocale', 'jsConfirm'], function() {
        // 定义状态保存变量
        function getWebRootPath() {
            var webroot = document.location.href;
            webroot = webroot.substring(webroot.indexOf('//') + 2, webroot.length);
            webroot = webroot.substring(webroot.indexOf('/') + 1, webroot.length);
            webroot = webroot.substring(0, webroot.indexOf('/'));
            var rootpath = webroot.length<=0 ? "" :  "/" + webroot;
            return rootpath;
        };
        var AppGlobal = Backbone.Model.extend({
            defaults: {
                currentStatus: "", //当前的状态（login表示登录状态，running表示已经登录并且session没有过期，sessionTimeOut表示session过期，beenKickedFromLogin表示从登录状态踢出）
                defaultLanguage: "en", //默认的语言
                shortLanguage: "en", //默认的是英语
                language: "en", //默认是英语
                charset: "UTF-8", //默认编码
                webroot: getWebRootPath(), //默认的webRoot是portal（默认没有webRoot）
                version: "v1.0", //默认的版本信息,
                defaultPortal: null, //默认的portal信息
                portalId: null, //当前的portalId信息
                currentMenu: null, //当前的菜单,
                userId: null,//当前用户,
                userCode: null
            }
        });
        window.Wyl.appGlobal =  new AppGlobal();
        //定义服务
        function callRemote(serviceName, data, success, error) {
            var option = {
                url: serviceName,
                data: JSON.stringify(data),
                type: 'POST',
                contentType: "text/json",
                dataType: "json",
                success: success
            };
            if (error) {
                option.error = error;
            }
            else {
                option.error = function(result) {console.log(result)};
            }
            Backbone.ajax(option);
        };

        window.Wyl.callService = callRemote;
        jsGrid.locale("zh-cn");
        //跳转到index
        require(['frm/js/modules/index/views/Index'], function(Index){
            var view = new Index({
                el: '#app-body'
            });
            view.index();
        })
    });
});