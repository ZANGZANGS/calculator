common = {

    callApi: function (url, param, successFunc, failFunc) {
        let base_url = location.href
        $.ajax({
            method: "POST",
            contentType: 'application/json',
            dataType   : 'json',
            url: base_url+url,
            data : JSON.stringify(param),

        })
        .done(function( res ) {
            if(typeof(successFunc) == 'function'){
                successFunc(res);
            }
        })
        .fail(function ( res ) {
            if(typeof(failFunc) == 'function'){
                failFunc(res);
            }
        });


    }


}