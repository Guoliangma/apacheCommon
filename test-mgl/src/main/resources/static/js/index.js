// **********************************************//
// index 客户端脚本
//
// @author Guoliang Ma
// @date 2020-06-18
// **********************************************//

var vm = new Vue({
    el : '#app',
    mixins: [atyTplMixin],
    data: function() {
        return {
            rs1: rs1, // 同步获取的方式, rs1需要在html底部定义
            exampleData: null,
        }
    },
    methods : {
        requestData: function() {
            var _this = this;
            // 此处请求用相对路径, 会自动拼接上html中指定的base做为contextPath
            // 如使用绝对路径则需要自己拼contextPath, 例子如下:
            // var contextPath = document.querySelector('base').href
            // contextPath = contextPath.substring(0, contextPath.length - 1);
            // contextPath = contextPath.substring(contextPath.lastIndexOf('/'));
            // Artery.ajax.get(contextPath + '/index/example', ...
            Artery.ajax.get('index/example', {
                params: {
                    param1: 'p1',
                    param2: 'p2'
                }
            }).then(function (result) {
                _this.exampleData = result;
            })
        }
    }
})