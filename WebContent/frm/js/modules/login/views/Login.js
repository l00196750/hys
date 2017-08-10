define([
    'text!frm/js/modules/login/templates/Login.html',
    'frm/js/modules/login/actions/logActions'
    ], function(Templ, actions) {

    return Backbone.View.extend({

        events: {
            // 'click button[id=loginBtn]': 'login'
            'click #loginBtn': 'login'
        },

        initialize: function() {},

        render: function() {
            this.$el.html(_.template(Templ, {}));
        },

        //登录按钮点击函数
        // login: function(e) {
        //     //TODO: 加入校验
        //     var username = $("#loginName").val();
        //     var password = $("#loginPwd").val();
        //     if (username !== '' && password !== '') {
        //         var sData = {
        //             username: username,
        //             password: password
        //         };
        //         actions.login(sData, function(data) {
        //             console.log('success:', data);
        //             if (data.loginSuccess) {
        //                 // 加载workspace页面
        //                 Wyl.appGlobal.set('userId', username);
        //                 Wyl.appGlobal.set('currentStatus', 'running');
        //             }

        //         }, function(err) {
        //             console.log('error');
        //         });
        //     }
        //     // Backbone.ajax({
        //     //     //登录的url地址
        //     //     // url: 'data/login.json',
        //     //     url: 'login',
        //     //     data: loginModel.toJSON(),
        //     //     type: 'POST',
        //     //     success: function(e) {
        //     //         console.log("success");
        //     //         globalApp.userInfo = '1';
        //     //         //设置登录成功后，跳转页面
        //     //         // appRouter.navigate("userlist", { trigger: true });
        //     //         appRouter.navigate("userlist", { replace: true, trigger: true  });

        //     //     },
        //     //     error: function() {
        //     //         console.log("fail");
        //     //     }
        //     // });

        // },

        login: function(e) {
            var loginName = $("#loginName").val();
            var loginPwd = $("#loginPwd").val();
            var loginModel = new (Backbone.Model)();
            loginModel.set({
                username: loginName,
                password: loginPwd
            });

            Backbone.ajax({
                url: 'login',
                data: loginModel.toJSON(),
                type: 'POST',
                success: function(data) {
                    if (data.loginSuccess) {
                        // 加载workspace页面
                        Wyl.appGlobal.set('userId', username);
                        Wyl.appGlobal.set('currentStatus', 'running');
                    }

                },
                error: function(err) {
                    console.log("fail");
                }
            });

        }
    });
});
