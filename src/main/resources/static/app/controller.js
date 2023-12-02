myApp.controller('myCtrl', function ($scope, $http, myService) {
    var vm = this;
    vm.formData = {};
    vm.listHierarchy = [];
    vm.colaboradores = [];
    vm.flattenedList = [];

    vm.getAvaliacao = function(score) {
        if (score >= 76) {
            return "Ótima";
        } else if (score >= 51) {
            return "Boa";
        } else if (score >= 26) {
            return "Média";
        } else {
            return "Fraca";
        }
    };

    (vm.getUsers = function () {
        myService.findAll().then(function (response) {
            console.log(response);
            vm.colaboradores = response.data;

        })
    })();

    vm.submitForm = function () {
        myService.postData(vm.formData)
            .then(function (response) {
                console.log('Response from server:', response.data);
                vm.getUsers();
                vm.findHierarchy();
                vm.formData = {};
            })
            .catch(function (error) {
                console.error('Error:', error);
            });
    };

    (vm.findHierarchy = function () {

        myService.findHierarchy().then(function (response) {
            vm.listHierarchy = response.data;

            vm.flattenedList = flattenColaboradores(vm.listHierarchy);

        })
    })();

    function flattenColaboradores(colaboradores) {
        let flattenedList = [];

        function flatten(colaborador) {
            flattenedList.push({
                name: colaborador.name,
                password: colaborador.password,
                passwordScore: colaborador.passwordScore
            });

            colaborador.subordinados.forEach(subordinado => flatten(subordinado));
        }

        colaboradores.forEach(colaborador => flatten(colaborador));

        return flattenedList;
    }

    $scope.myWelcome = 'asdsarsarsa';
});
