/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
$(function() {
    $.mask.definitions['~'] = "[+-]";
    $("#cpf").mask("999.999.999-99");
    
    openDialog = function(data){
        $('#modal-3').dialog({
             modal:true,
             width: 600
             
         });
    }
})



