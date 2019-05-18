(function($) {
    //转变成数组
    var permissionArr = new Array();
    if(permissions){ //这里相当于隔离了未登录
        permissionArr = window.permissions.toString().split(",");
//		console.log(permissionArr);
    }
    $.extend({
        shiro:{
            hasPermission:function(permission){
                var i = 0, length = permissionArr && permissionArr.length;
                for (; i < length; i++) if (permissionArr[i] === permission) return true;
                return false;
            }
        }
    });
})(jQuery, undefined);
