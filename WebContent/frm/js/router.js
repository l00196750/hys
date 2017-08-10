/**
 * 设置路由Map表
 * @Author   WOJUSTME-XRH
 * @DateTime 2016-07-19T14:15:48+0800
 * @param    {Object}                   [description]
 * @return   {[type]}                   [description]
 */
define(['backbone'], function() {
    // TODO:加载所有的目录
    var routesMap = {
        '': 'js/login/loginGlue.js',
        // 'userlist': 'js/userlist/userlistGlue.js'
        'userlist': 'js/userlist/userlistGlue.js'
    };

    var Router = Backbone.Router.extend({
        routes: routesMap
    });

    window['appRouter'] = new Router();
    //彻底用on route接管路由的逻辑，这里route是路由对应的value
    appRouter.on('route', function(route, params) {
        if (route === routesMap['']) {
            // 登录
            require([route], function(glue) {
                if (appRouter.currentGlue && appRouter.currentGlue !== glue) {
                    appRouter.currentGlue.onRouteChange && appRouter.currentGlue.onRouteChange();
                }
                appRouter.currentGlue = glue;
                glue.apply(null, params);
            });
        }
        else {
            if (globalApp.userInfo) {
                require([route], function(glue) {
                    if (appRouter.currentGlue && appRouter.currentGlue !== glue) {
                        appRouter.currentGlue.onRouteChange && appRouter.currentGlue.onRouteChange();
                    }
                    appRouter.currentGlue = glue;
                    glue.apply(null, params);
                });
            }
            else {
                appRouter.navigate('', { trigger: true });
            }
        }
    });

    return appRouter;
});