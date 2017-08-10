(function(win) {
    //先获得页面中baseUrl数值
    // var baseUrl = document.getElementById('main').getAttribute('data-baseurl');
    var config = {
        paths: {
            zepto: 'frm/lib/zepto.min',
            jquery: 'frm/lib/jquery',
            underscore: 'frm/lib/underscore',
            backbone: 'frm/lib/backbone',
            text: 'frm/lib/text',
            bootstrap: 'frm/lib/bootstrap.min',
            jsGrid: 'frm/lib/jsgrid/jsgrid',
            jsGridLocale: 'frm/lib/jsgrid/i18n/jsgrid-zh-cn',
            jsConfirm: 'frm/lib/jqueryConfirm/jquery-confirm'
        },
        shim: {
            'underscore': {
                exports: '_'
            },
            'jquery': {
                exports: '$'
            },
            'zepto': {
                exports: '$'
            },
            'backbone': {
                deps: ['underscore', 'jquery'],
                exports: 'Backbone'
            },
            'bootstrap': {
                deps: ['jquery'],
                exports: '$'
            },
            'jsGrid': {
                deps: ['jquery'],
                exports: 'jsGrid'
            },
            'jsGridLocale': {
                deps: ['jsGrid'],
                exports: 'jsGridLocale'
            },
            'jsConfirm': {
                deps: ['jsGrid'],
                exports: 'jsConfirm'
            }
        },

        deps: ['frm/js/main']
    };

    require.config(config);
    // require(['backbone', 'bootstrap', 'underscore', 'router', 'jsGrid', 'jsGridLocale'], function() {
    //     //开启Backbone的路由控制
    //     win.globalApp = {};
    //     jsGrid.locale("zh-cn");
    //     Backbone.history.start();
    // });

})(window);