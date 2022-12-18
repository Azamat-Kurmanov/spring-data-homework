angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8280/appapi/v1';
    $scope.prdList = [];
    $scope.pages = [];
    $scope.firstPage = 1;
    $scope.pagesLimit = 10;
    $scope.numberOfPages = 0;

    //---Загрузка списка
    $scope.loadProducts = function (firstPage) {
        $http.get(contextPath + '/products')
            .then(function (response) {
                $scope.prdList = response.data;
                $scope.paging(response.data);
                $scope.checkPage(firstPage);
            });
    };

    //----Удаление товара по id
    $scope.deleteProduct = function(productId){
        $http.delete(contextPath + '/products' + productId)
            .then(function (response) {
                if (response.status==200){
                    const page = $scope.getMaxMinRows(productId);
                    $scope.loadProducts(page);
                }
            });
    }

    //---Получить страницу по удаляемому id
    $scope.getMaxMinRows = function (rowId){
        let page = 0;
        let numberOfPagesInt = parseInt($scope.prdList.length)/parseInt($scope.pagesLimit);
        const numberOfPagesFloat = parseFloat($scope.prdList.length)/parseInt($scope.pagesLimit);

        if (numberOfPagesFloat>Math.floor(numberOfPagesInt)) {
            numberOfPagesInt = Math.floor(numberOfPagesFloat+1)
        }
        for (let i = 1; i <= numberOfPagesInt; i++) {
            const numberOfPages = $scope.prdList.length/numberOfPagesInt > $scope.pagesLimit ? $scope.prdList.length : parseInt($scope.pagesLimit) * i;
            for (let j = ((i * parseInt($scope.pagesLimit))-parseInt($scope.pagesLimit)+1); j <= numberOfPages; j++) {
                j = j>0 ? j : 1;
                if (rowId===j){
                    page = i;
                    // console.log('page: ' + page + ' row: ' + i);
                    break;
                }
            }
        }
        return page;
    }

    //----Отображение нумераций страниц
    $scope.paging = function(prdList){
        $scope.pages = [];
        let numberOfPagesInt = parseInt(prdList.length)/parseInt($scope.pagesLimit);
        const numberOfPagesFloat = parseFloat(prdList.length)/parseInt($scope.pagesLimit);

        if (numberOfPagesFloat>Math.floor(numberOfPagesInt)) {
            numberOfPagesInt = Math.floor(numberOfPagesFloat+1)
        }

        $scope.numberOfPages = numberOfPagesInt;
        for (let i = 1; i <= numberOfPagesInt; i++) {
            $scope.pages.push(i);
        }
    }

    //----Отображение части списка согласно его страницы
    $scope.checkPage = function(page){
        $scope.ProductListWithPages = [];
        const maxRows = page * parseInt($scope.pagesLimit);
        const firstRow = maxRows - parseInt($scope.pagesLimit);
        if ($scope.prdList.length>0) {
            const maxRowsNumber = $scope.prdList.length > maxRows ? maxRows : $scope.prdList.length;
            for (let i = firstRow; i < maxRowsNumber; i++) {
                $scope.ProductListWithPages.push($scope.prdList[i]);
            }
        }
    }

    $scope.loadProducts($scope.firstPage);
});