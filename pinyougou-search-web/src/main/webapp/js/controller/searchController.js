//控制层   $location:获取当前url的参数的序列化json对象
app.controller('searchController', function ($scope, $controller, searchService) {

    $controller('baseController', {$scope: $scope});//继承

   $scope.search=function () {
       searchService.search($scope.searchMap).success(function (response) {
           $scope.resultMap=response;
       })
   }

});
