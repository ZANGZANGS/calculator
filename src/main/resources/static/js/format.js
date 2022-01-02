format = {

    convertMoneyComma : function (money){
        let {number, decimal} = format.splitNumber(money);
        return number.replace(/\B(?=(\d{3})+(?!\d))/g, ',') + '.' + decimal;
    },

    removeMoneyComma : function (money){
        let {number, decimal} = format.splitNumber(money);
        return number.replace(/[^0-9]/g, "") + '.' + decimal;
    },

    convertComma : function (money){
        let {number} = format.splitNumber(money);
        return number.replace(/\B(?=(\d{3})+(?!\d))/g, ',');
    },

    removeComma : function (money){
        let {number} = format.splitNumber(money);
        return number.replace(/[^0-9]/g, "");
    },

    splitNumber : function (money){
        let number, decimal;
        let moneyStr = String(money);

        number= String(money).split(".")[0] || '0';

        if(moneyStr.indexOf('.') <0) {
            decimal='00';
        }else{
            decimal = moneyStr.split('.')[1].substr(0, 2) || '00';
            if(decimal.length <2) decimal = decimal+'0';
        }
        return {number, decimal};
    }

}
