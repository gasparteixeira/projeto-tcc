/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$(function() {
    $.mask.definitions['~'] = "[+-]";
    $("#cpf").mask("999.999.999-99");
    $(".mask_card").mask("9999 9999 9999 9999",{placeholder:" "});
    
    openDialog = function(data){
        $('#modal-3').dialog({
             modal:true,
             width: 600
             
         });
    }
})



