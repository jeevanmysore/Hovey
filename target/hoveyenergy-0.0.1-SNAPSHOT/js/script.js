function hide_placeholder(input) {
	if (input.value == input.defaultValue) {
		input.value = "";
		input.style.color = '#000000';
	}
}
function show_placeholder(input) {
	if (input.value == "") {
		input.value = input.defaultValue;
		input.style.color = 'grey';
	}
}
jQuery("#check_box1").click(function (){
	jQuery(".check_box1").toggle();
});
jQuery("#check_box2").click(function (){
	jQuery(".check_box2").toggle();
});	
jQuery("#check_box3").click(function (){
	jQuery(".check_box3").toggle();
});	
jQuery("#check_box4").click(function (){
	jQuery(".check_box4").toggle();
});
if(jQuery('.screen6').width()<='320'){
	var orient = 'horizontal';
	jQuery('#zoom_toggle').attr('class','zoom_toggle horizontal');
}else{
	var orient = 'vertical';
}	
jQuery(".noUiSlider").noUiSlider({
    range: [0, 100]
   ,start: 50
   ,handles: 1
   ,orientation: orient
});