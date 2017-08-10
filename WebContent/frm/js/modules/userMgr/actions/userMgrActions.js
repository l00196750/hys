define(function(){
	return {
		qryUserPage: function(data, success) {
			Wyl.callService('qryUserPage', data, success);
		}
	}
});