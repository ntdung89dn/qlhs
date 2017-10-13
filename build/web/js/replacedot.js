/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$('.repalacedot').on('keyup',function(){
   var oldInput = $(this).val();
   var newInput = oldInput.replace(".",",");
   $(this).val(newInput);
});