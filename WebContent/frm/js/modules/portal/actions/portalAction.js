define(function(){
	return {
		qryRole: function(data, success) {
			Wyl.callService('getRole', data, success);
		},

		qryMenu: function(data, success) {
			Wyl.callService('getUserMenu', data, success);
		}
	}
});