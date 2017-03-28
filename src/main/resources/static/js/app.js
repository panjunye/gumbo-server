var controller = function($mdSidenav) {
    this.toggleNavigation = function() {
        $mdSidenav('navigation-drawer').toggle();
    };
};

var app = angular.module('ngMaterialSidemenu');

app.requires.push('ui.router','ngMaterial','ngMessages','md.data.table');

app.controller('AppController', controller);

function AddAppController($scope,$http,$mdDialog) {

   $scope.addApp = function () {
       $http.post('api/apps',$scope.app).then(function (response) {
            $mdDialog.show(
                $mdDialog.alert()
                    .clickOutsideToClose(true)
                    .title('添加成功')
                    .textContent($scope.app.name)
                    .ok('确定')
            );
       },function (response) {
           $mdDialog.show(
               $mdDialog.alert()
                   .clickOutsideToClose(true)
                   .title('添加失败')
                   .textContent(response.data.error)
                   .ok('确定')
           );
       });
   }
};

function SetAppController($scope,$http,$mdDialog) {

    $http.get('api/apps').then(function (response) {
        $scope.apps = response.data;
        if($scope.apps.length > 0){
            $scope.app = $scope.apps[0];
        }


    },function (response) {
       $mdDialog.show(
           $mdDialog.alert()
               .clickOutsideToClose(true)
               .title('无法获取应用列表')
               .textContent(response.data.error)
               .ok('确定')
       );
    });


    $scope.$watch('app',function (app) {
        if(angular.isDefined(app)){
            console.log(app);
            $http.get('api/apps/' + app.name + '/apks').then(function (response) {
                $scope.apks = response.data;
                console.log($scope.apks);
            },function (response) {
                console.log(response.data.error);
            });
        }
    });

    $scope.deleteApp = function () {
       $mdDialog.show(
           $mdDialog.confirm()
               .clickOutsideToClose(true)
               .title('是否移除'+$scope.app.name + '?')
               .textContent('此操作会删除所有已经上传的APK文件')
               .ok('确定')
               .cancel('取消')
       ).then(function () {
           $http.delete('api/apps/' + $scope.app.name).then(function (response) {
               var index = $scope.apps.indexOf($scope.app);
               if(index > -1){
                   $scope.apps.splice(index,1);
                   if($scope.apps.length > 0){
                       $scope.app = $scope.apps[index - 1];
                   }else{
                       $scope.app = undefined;
                   }
               }
           },function (response) {
               $mdDialog.show(
                   $mdDialog.alert()
                       .clickOutsideToClose(true)
                       .title('移除失败')
                       .textContent(response.data.error)
                       .ok('确定')
               );
           })
       });
   };

    $scope.addApk = function(ev) {
        var app = $scope.app;
        console.log(app);
        $mdDialog.show({
            controller: AddApkDialogController,
            templateUrl: 'dialog/addApk.dialog.html',
            targetEvent: ev,
            clickOutsideToClose:false
        })
        .then(function (apk) {
            $scope.apks.unshift(apk);
            app.patch = false;
            $mdDialog.show(
                $mdDialog.alert({
                    skipHide:true,
                    clickOutsideToClose:true,
                    textContent:'上传成功',
                    ok:'确定'})
            );
        },function () {
            
        });

        function AddApkDialogController($scope, $mdDialog) {

            $scope.fileSelected = function (fileInput) {
                var apk = fileInput.files[0];
                fileInput.value = ''; //需要置空input，否则用户选择同一个文件时不会触发.
                if(!apk.name.endsWith('.apk')){
                    $mdDialog.show(
                        $mdDialog.alert()
                            .clickOutsideToClose(true)
                            .title('只能上传APK文件')
                            .textContent('')
                            .ok('确定')
                    );
                }else{
                    $scope.apk = apk;
                    $scope.$apply();
                }

            };

            $scope.hide = function() {
                $mdDialog.hide();
            };

            $scope.cancel = function() {
                $mdDialog.cancel();
            };

            $scope.upload = function(uploadLog) {
                console.log(app.name);
                var formData = new FormData();

                formData.append('apk', $scope.apk);

                formData.append('updateLog',uploadLog);

                var postForm =
                {
                    method:'POST',
                    url:'api/apps/' + app.name +'/apks',
                    data:formData,
                    headers:{
                        'Content-Type': undefined
                    },
                    uploadEventHandlers: {
                        progress: function (e) {
                            if (e.lengthComputable) {
                                $scope.progress = (e.loaded / e.total) * 100;
                            }
                        }
                    }
                };

                $http(postForm)
                .then(function (response) {
                    $mdDialog.hide(response.data);
                },function (response) {
                    $mdDialog.show(
                        $mdDialog.alert({
                            skipHide:true,
                            clickOutsideToClose:true,
                            textContent:response.data.error,
                            ok:'确定'})
                    );
                });


            };
        }
    };

    $scope.deleteApk = function(apk){
        $mdDialog.show(
            $mdDialog.confirm()
                .clickOutsideToClose(true)
                .title('是否要删除？')
                .textContent(apk.versionName)
                .cancel('取消')
                .ok('确定')
        ).then(function () {
            $http.delete('api/apps/' + $scope.app.name + '/apks/' + apk.versionCode).then(function () {
                var index = $scope.apks.indexOf(apk);
                if(index > -1){
                    $scope.apks.splice(index,1);
                }
                if(index === 0){
                    $scope.app.patch = true;
                }
            },function (response) {
                $mdDialog.alert()
                    .clickOutsideToClose(true)
                    .title('删除失败')
                    .textContent(apk.versionName)
                    .ok('确定')
            });
        });
    };

    $scope.patch = function () {

        $scope.app.patching = true;

        $http.patch('api/apps/' + $scope.app.name).then(function (response) {
            $scope.app.patch = true;
            $scope.app.patching = false;
            $mdDialog.show(
                $mdDialog.alert()
                    .clickOutsideToClose(true)
                    .title('生成成功')
                    .textContent(response.data.error)
                    .ok('确定')
            );
        },function (response) {
            $scope.app.patching = false;
            $mdDialog.show(
                $mdDialog.alert()
                    .clickOutsideToClose(true)
                    .title('生成失败')
                    .textContent(response.data.error)
                    .ok('确定')
            );
        });
    }
}

app.config(function($stateProvider) {

    var addAppState = {
        name: 'addApp',
        url: '/setting/app/add',
        templateUrl:'tmpl/setting.app.add.tmpl.html',
        controller:AddAppController
    };

    var setAppState = {
        name: 'setApp',
        url: '/setting/app/set',
        templateUrl:'tmpl/setting.app.set.tmpl.html',
        controller:SetAppController
    };

    $stateProvider.state(addAppState);
    $stateProvider.state(setAppState);
});