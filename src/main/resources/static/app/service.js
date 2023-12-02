const apiUrl = 'http://localhost:8080/api/';
myApp.service('myService', ['$http', function ($http) {
    this.postData = function (data) {
        return $http.post(apiUrl + 'colaborador/save', data);
    };

    this.findHierarchy = function () {
        return $http.get(apiUrl + 'colaborador/findHierarchy');
    };

    this.findAll = function () {
        return $http.get(apiUrl + 'colaborador/findAll');
    };
}]);