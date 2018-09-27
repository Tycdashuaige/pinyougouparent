//首页控制器
app.controller('indexController', function ($scope, $controller, loginService) {
    $scope.showName = function () {
        loginService.showName().success(
            function (response) {
                $scope.loginName = response.loginName;
            }
        );
    }
});
