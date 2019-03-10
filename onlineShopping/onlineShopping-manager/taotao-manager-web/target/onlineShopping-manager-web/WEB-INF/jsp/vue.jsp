
<%--
  Created by IntelliJ IDEA.
  User: iu
  Date: 2019/2/26
  Time: 16:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<script src="/js/vue.min.js"></script>
<head>

</head>
<div id="app">
    {{ message | capitalize }}
</div>

<script>
    new Vue({
        el: '#app',
        data: {
            message: ''
        },
        filters: {
            capitalize: function (value) {
                if (!value) {return 'nihao'}
                value = value.toString()
                return value.charAt(0).toUpperCase() + value.slice(1)
            }
        }
    })
</script>

</html>
