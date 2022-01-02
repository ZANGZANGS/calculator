common = {

    callApi: function (url, param, successFunc, failFunc) {
        let base_url = location.href
        $.ajax({
            method: "POST",
            url: base_url+url,
            data: param
        })
        .done(function( res ) {
            if(res.success == true){
                if(typeof(successFunc) == 'function'){
                    successFunc(res);
                }
            }else{
                alert('얘기치 못한 오류가 발생하였습니다.\n잠시후 시도해 주십시오.');
            }

        })
        .fail(function ( res ) {
            if(typeof(failFunc) == 'function'){
                failFunc(res);
            }
        });


    }


}