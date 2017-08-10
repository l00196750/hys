define(function(){
	return {
		login: function(data, success) {
			Wyl.callService('login', data, success);
		}
	}
});