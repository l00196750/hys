define([
	'text!frm/js/modules/portal/htmls/Portal.html',
	'frm/js/modules/portal/actions/portalAction'
	], function(Templ, actions){
	return Backbone.View.extend({
		events: {
			'click #portal-menulist > li': 'menuClick'
		},

		initialize: function() {
			_.extend(window, Backbone.Events);
  			window.onresize = function() { window.trigger('resize') };
			this.listenTo(window, 'resize', function(){
				console.log('```````````````````resize`````````````````');
				this.resize();
			}.bind(this));
		},

        render: function() {
            this.$el.html(_.template(Templ, {}));
            this.afterRender();
        },

        afterRender: function() {
        	this.getNodes();
			this.getMenu();
			this.resize();
        },

        getNodes: function() {
        	this.$header = $('#portal-header');
        	this.$navi = $('#portal-navbar');
        	this.$body = $('#portal-body');
        	this.$menu = $('#portal-menulist');
        },

        getMenu: function() {
        	var sData = {
        		userId: Wyl.appGlobal.userId
        	};
        	this.menuData = [
        		{
        			MENU_NAME: '用户管理',
        			MENU_CODE: '',
        			MENU_PATH: 'frm/js/modules/userMgr/views/UserMgr',
                    ICON: 'glyphicon glyphicon-user'
        		},
                {
                    MENU_NAME: '角色管理',
                    MENU_CODE: '',
                    MENU_PATH: 'frm/js/modules/userMgr/views/UserMgr',
                    ICON: 'glyphicon glyphicon-user'
                }
        	];
        	this.createMenuDiv();
        },

        createMenuDiv: function() {
        	var that = this;
        	var html = [];
        	_.each(this.menuData, function(elem, index){
        		html.push('<li id="');
        		html.push(index);
        		html.push('"><a class="');
                html.push(elem.ICON);
                html.push('" href="javascript:void(0)">&nbsp;');
        		html.push(elem.MENU_NAME);
        		html.push('</a></li>')
        	}.bind(that));
        	this.$menu.html(html.join(''));
        },

        menuClick: function(e) {
        	var $div = $(e.currentTarget);
        	var id = $div.attr('id');
        	if (id && this.menuData[id]) {
        		var path = this.menuData[id].MENU_PATH;
        		if (path) {
        			require([path], function(View){
        				var view = new View({
        					el: '#portal-body'
        				});
        				view.render();
        			});
        		}
        	}
        },

        resize: function() {
        	var total = window.innerHeight;
        	var bodyHeight = total - this.$header.outerHeight() - this.$navi.outerHeight() - 6;
        	this.$body.css('height', bodyHeight+'px');
        	//获取当前view，调用resize
        }
	});
});