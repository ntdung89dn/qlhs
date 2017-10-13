/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var filterPage = function(){
    $.ajax({
        type: "POST",
        url:"filterrole",
        dataType : "json",
        success: function (data) {
            console.log(data);
        }
    });
};
