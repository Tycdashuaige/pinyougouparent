//控制层   $location:获取当前url的参数的序列化json对象
app.controller('searchController', function ($scope, $controller, $location, searchService) {

    $controller('baseController', {$scope: $scope});//继承

    $scope.search = function () {
        $scope.searchMap.pageNo = parseInt($scope.searchMap.pageNo);
        searchService.search($scope.searchMap).success(function (response) {
            $scope.resultMap = response;
            buildPageLabel();
        })
    };

    $scope.searchMap = {
        'keywords': '',
        'category': '',
        'brand': '',
        'spec': {},
        'price': '',
        'pageNo': 1,
        'pageSize': 40,
        'sort': '',
        'sortField': ''
    };

    $scope.loadkeywords = function () {
        if ($location.search()['keywords'] != null) {
            $scope.searchMap.keywords = $location.search()['keywords'];
        }
        $scope.search();
    };

    $scope.sortSearch = function (sortField, sort) {
        $scope.searchMap.sort = sort;
        $scope.searchMap.sortField = sortField;
        $scope.searchMap.pageNo = 1;
        $scope.search();
    };

    /*隐藏品牌列表*/
    $scope.keywordsIsBrand = function () {
        for (var i = 0; i < $scope.resultMap.brandList.length; i++) {
            if ($scope.searchMap.keywords.indexOf($scope.resultMap.brandList[i].text) > 0) {
                return true;
            }
            return falsep
        }

    };
    $scope.addSearchItem = function (key, value) {
        if (key == 'brand' || key == 'category' || key == 'price') {
            $scope.searchMap[key] = value;
        } else {
            $scope.searchMap.spec[key] = value;
        }
        $scope.searchMap.pageNo = 1;
        $scope.search();
    };

    $scope.removeSearchItem = function (key) {
        if (key == 'brand' || key == 'category' || key == 'price') {
            $scope.searchMap[key] = "";
        } else {
            delete $scope.searchMap.spec[key];
        }
        $scope.searchMap.pageNo = 1;
        $scope.search();
    };

    buildPageLabel = function () {
        $scope.pageLabel = [];
        var maxPageNo = $scope.resultMap.totalPages;
        var firstPage = 1;
        var lastPage = maxPageNo;
        $scope.firstDot = true;
        $scope.lastDot = true;
        if ($scope.resultMap.totalPages > 5) {
            if ($scope.searchMap.pageNo <= 3) {
                lastPage = 5;
                $scope.firstDot = false;
            } else if ($scope.searchMap.pageNo >= lastPage - 2) {
                firstPage = maxPageNo - 4;
                $scope.lastDot = false;
            } else {
                firstPage = $scope.searchMap.pageNo - 2;
                lastPage = $scope.searchMap.pageNo + 2;
            }
        }
        for (var i = firstPage; i <= lastPage; i++) {
            $scope.pageLabel.push(i);
        }
    };

    $scope.queryByPage = function (pageNo) {
        if (pageNo < 1 || pageNo > $scope.resultMap.totalPages) {
            return;
        }
        $scope.searchMap.pageNo = pageNo;
        $scope.search();
    }

    $scope.isTopPage = function () {
        if ($scope.searchMap.pageNo == 1) {
            return true;
        }
        return false;
    };

    $scope.isEndPage = function () {
        if ($scope.searchMap.pageNo == $scope.resultMap.totalPages) {
            return true;
        }
        return false;
    }


});
