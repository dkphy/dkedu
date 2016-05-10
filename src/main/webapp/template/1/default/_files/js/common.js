/**
 * Created by slimcheng on 16/4/29.
 */
$(".menu_btn").click(function(){
    $(".header .menu").addClass("show");
    $(".header").append("<div class='menu_over' onclick='close_menu(this)'></div>");
})

function close_menu(obj){
    $(obj).remove();
    $(".header .menu").removeClass("show");
}