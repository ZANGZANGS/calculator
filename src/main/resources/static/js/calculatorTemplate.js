$(document).ready(function (){

    event();
});


function event(){

    /**
     * 환전 국가 선택 이벤트
     */
    $('#currencies').change(function(){
        $('#exchange_rate').text($(this).prop('value')); // 환율
        $('#currency_kind').text($('#currencies').children(':selected').text()); //화폐 종류
        hideMsg();
    });

    /**
     * 송금 금액 INPUT 값 콤마(,) 찍는 이벤트
     */
    $('#remittance_amount').keyup(function (){
        $(this).val(format.convertComma(format.removeComma($(this).val())));
    });

    /**
     * submit 버튼 클릭 이벤트
     */
    $('#submit_btn').click(function (){


        //Number()*Number()
        let x = new Decimal(format.removeMoneyComma($('#remittance_amount').val()));
        let y = new Decimal($('#exchange_rate').text());
        let z = x.mul(y);
        let msg = '';

        if(x.toNumber()<0 || x.toNumber()> 10000){
            msg = '송금액이 바르지 않습니다';
            $('#result_msg').css('color','red');
            changeMsgText('result_msg', msg);
        }else{
            //수취 금액은 111.000.00 KRW 입니다.

            /* 클라이언트 계산 버전 주석 처리 */
            //const amt = format.convertMoneyComma(String(z.toNumber().toFixed(2)));
            //msg = '수취 금액은 ' + amt + ' '+ $('#currencies').children(':selected').text() + '입니다.';
            //$('#result_msg').css('color','black');


            //서버 api convert 호출
            let param = {
                'from' : $('#source').val(),
                'to' : $('#currencies').children(':selected').text(),
                'amount' : format.removeMoneyComma($('#remittance_amount').val()),
                'rate' : format.removeMoneyComma($('#currencies').val()),
            }
            common.callApi('convert',param, successFunc, failFunc);
            $('#result_msg').css('color','black');
        }

        showMsg();
    });

};

/**
 * ajax 성공함수 이벤트
 * @param res
 */
function successFunc(res){

    if(typeof(res ?? undefined)== 'undefined'){
        return;
    }
    let {amount} = res;

    let msg = '수취 금액은 ' + format.convertMoneyComma(amount) + $('#currencies').children(':selected').text() + ' 입니다.';
    changeMsgText('result_msg', msg);

}

function failFunc(res){
    if(typeof(res ?? undefined)== 'undefined'){
        return;
    }
    alert('얘기치 못한 오류가 발생하였습니다.\n잠시후 시도해 주십시오.');
}

function hideMsg(){
    $('#result_msg').hide();
}

function showMsg(){
    $('#result_msg').show();
}

function changeMsgText(id, msg){
    $('#'+id).text(msg);
}


