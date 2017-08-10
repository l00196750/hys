define(function(){
	return Backbone.View.extend({
		initialize: function () {
            Wyl.appGlobal.on("change:currentStatus", this.currentStatusChange, this); //监听menuView的菜单加载完的事件
            document.title = 'TEST';
        },

        index: function () {
            var status = "login";
            // 获取用户的登录情况
            Wyl.appGlobal.set("currentStatus", status);
        },

        currentStatusChange: function () { //登录状态改变
            if ("login" == Wyl.appGlobal.get("currentStatus")) { //如果已经登录了，则修改成PotalView，否则变成LoginView
                require(['frm/js/modules/login/views/Login'], function(View){
                	var view = new View({
                		el: '#app-body'
                	});
                	view.render();
                })
            } else if ("running" == Wyl.appGlobal.get("currentStatus")) {
                require(['frm/js/modules/portal/views/Portal'], function(View){
                	var view = new View({
                		el: '#app-body'
                	});
                	view.render();
                })
            } else if ("sessionTimeOut" == Wyl.appGlobal.get("currentStatus")) {
            	// fish.store.set("reLogin", i18n.SESSION_TIME_OUT_REASON);
				document.location.href = Wyl.appGlobal.get('webroot');
            } else if ("beenKickedFromLogin" == Wyl.appGlobal.get("currentStatus")) {
            	// fish.store.set("reLogin", i18n.SESSION_TIME_OUT_BEEN_KICKED);
				document.location.href = Wyl.appGlobal.get('webroot');
            }
        }
	});
});