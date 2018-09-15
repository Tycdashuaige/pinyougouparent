//控制层
app.controller('portalController', function ($scope, $controller, portalService) {

    $controller('baseController', {$scope: $scope});//继承


    $scope.ContentList=[];
    $scope.findByCategoryId=function(categoryId){
        portalService.findByCategoryId(categoryId).success(function (response) {
            $scope.ContentList[categoryId]=response;
        })
    }


    /* $scope.status=["无效","有效"];
     $scope.findContentCategoryList=function(){
         contentCategoryService.findAll().success(function (response) {
             $scope.ContentCategoryList=response;
         })
     }*/

    $scope.entity={};
    /*
    $scope.uploadFile = function () {
        uploadService.uploadFile().success(function (response) {
            if (response.success) {
                $scope.entity.pic = response.message;
            } else {
                alert("上传失败");
            }
        }).error(function () {
            alert("上传出错");
        })

    };*/

    //读取列表数据绑定到表单中
    $scope.findAll = function () {
        portalService.findAll().success(
            function (response) {
                $scope.list = response;
            }
        );
    }

    //分页
    $scope.findPage = function (page, rows) {
        portalService.findPage(page, rows).success(
            function (response) {
                $scope.list = response.rows;
                $scope.paginationConf.totalItems = response.total;//更新总记录数
            }
        );
    }

    //查询实体
    $scope.findOne = function (id) {
        portalService.findOne(id).success(
            function (response) {
                $scope.entity = response;
            }
        );
    }

    //保存
    $scope.save = function () {
        var serviceObject;//服务层对象
        if ($scope.entity.id != null) {//如果有ID
            serviceObject = portalService.update($scope.entity); //修改
        } else {
            serviceObject = portalService.add($scope.entity);//增加
        }
        serviceObject.success(
            function (response) {
                if (response.success) {
                    //重新查询
                    $scope.reloadList();//重新加载
                } else {
                    alert(response.message);
                }
            }
        );
    }


    //批量删除
    $scope.dele = function () {
        //获取选中的复选框
        portalService.dele($scope.selectIds).success(
            function (response) {
                if (response.success) {
                    $scope.reloadList();//刷新列表
                }
            }
        );
    }

    $scope.searchEntity = {};//定义搜索对象

    //搜索
    /* $scope.search = function (page, rows) {
         portalService.search(page, rows, $scope.searchEntity).success(
             function (response) {
                 $scope.list = response.rows;
                 $scope.paginationConf.totalItems = response.total;//更新总记录数
             }
         );
     }*/

    $scope.search=function () {
        location.href="http://localhost:9104/search.html#?keywords="+$scope.keywords;
    }

});