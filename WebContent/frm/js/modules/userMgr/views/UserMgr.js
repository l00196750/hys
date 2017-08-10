define([
	'text!frm/js/modules/userMgr/templates/UserMgr.html',
	'frm/js/modules/userMgr/actions/userMgrActions'
	], function(Templ, actions){
	return Backbone.View.extend({
        events: {
            'click #user-mgr-btn-search': 'search',
            'click #user-mgr-btn-reset': 'reset',
            'click #user-mgr-btn-add': 'add',
            'click #user-mgr-btn-modify': 'modify',
            'click #user-mgr-btn-delete': 'delete',
            'click #user-mgr-search-type-list > li': 'searchTypeChange'
        },
		initialize: function() {

		},

		render: function() {
			this.getNodes();
            this.$el.html(_.template(Templ, {}));
            this.afterRender();
		},

		afterRender: function() {
			this.getNodes();
            this.resize();
			this.renderGrid();
		},

		getNodes: function() {
			this.$toolbar = $('#user-mgr-toolbar');
            this.$searchTypeCode = $('#user-mgr-search-type-code');
            this.$searchTypeName = $('#user-mgr-search-type-name');
            this.$searchInput = $('#user-mgr-search-input');
            this.$gridContainer = $('#user-mgr-grid-container');
			this.$grid = $('#user-mgr-grid');
		},

		renderGrid: function() {
            var that = this;
            this.$grid.jsGrid({
                width: "100%",
                height: "100%",

                autoload: true,
                paging: true,
                pageLoading: true,
                pageSize: 15,
                pageIndex: 1,

                fields: [
                    { name: "USER_CODE", type: "text", width: 150, title:"编码"},
                    { name: "USER_NAME", type: "text", width: 50, title:"姓名" },
                    { name: "PHONE", type: "text", width: 200, title:"联系电话" },
                    { name: "EMAIL", type: "text", title: "邮箱" }
                ],

                controller: {
                    loadData: function(filter) {
                        var startIndex = (filter.pageIndex - 1) * filter.pageSize;
                        return {
                            // data: db.clients.slice(startIndex, startIndex + filter.pageSize),
                            // itemsCount: db.clients.length
                            data: [],
                            itemsCount: 30
                        };
                    }
                },

                rowClick: function(item) {
                    this.rowClick(item);
                }.bind(that),
            });
		},

        searchTypeChange: function(e) {
            var div = e.currentTarget.children[0];
            var name = div.innerHTML;
            var code = div.getAttribute('name');
            this.$searchTypeCode.attr('name', code);
            this.$searchTypeName.html(name);
        },

        rowClick: function(item) {

        },

        search: function() {
            console.log('search click');
        },

        reset: function() {

        },

        add: function() {
            // $("#grid").jsGrid("insertItem", { Name: "John", Age: 25, Country: 2 }).done(function() {
            //     console.log("insertion completed");
            // });
            var dialog = $.dialog({
                title: '新增',
                content: 'Simple modal!',
            });
        },

        modify: function() {
            // $("#grid").jsGrid("updateItem", item, { ID: 1, Name: "John", Age: 25, Country: 2 }).done(function() {
            //     console.log("update completed");
            // });
        },

        delete: function() {
            // 弹出提示框
        },

		resize: function() {
			var parentHeight = this.$el.height();
            var gridHeight = parentHeight - 2 - this.$toolbar.outerHeight();
            this.$gridContainer.css('height', gridHeight+'px');
		}
	});
});