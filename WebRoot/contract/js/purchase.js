(function ($) {

    $.fn.extend({
        wrapSelect: function (settings) {
            var $target = $(this);

            $target.prepend("<select></select>-<select><option>请选择</option></select><input type='hidden' name='"+settings.name  +"' id='"+settings.name  +"'><input type='hidden' name='"+settings.name  +"Id' id='"+settings.name  +"Id'>");

            var $level1 = $($target.find("select")[0]);
            var $level2 = $($target.find("select")[1]);
            var nSettings = $.extend({defaultLabel: "请选择", defaultValue: "", label: "label", value: "value", root: "result", selectVal: ""}, settings);
            var nParam = $.extend({}, settings.param);
            $level1.change(function () {
                nSettings.param.pid = $(this).val();
                if($(this).val()=="1"){
                    $("#"+settings.name).val($(this).find("option:selected").text());
                    $("#"+settings.name+"Id").val($(this).val());
                }
                else{
                    $("#"+settings.name).val("");
                    $("#"+settings.name+"Id").val("");
                }
                _loadData($level2, nSettings);
            });

            $level2.change(function(){
                $("#"+settings.name).val($level1.find("option:selected").text()+"-"+$(this).find("option:selected").text());
                $("#"+settings.name+"Id").val($(this).val());

            })

            if (settings.selectVal && settings.selectVal.length > 1) {
                settings.selectVal = settings.selectVal.charAt(0);

                nSettings.param = nParam;
                nSettings.param.pid = settings.selectVal;
                _loadData($level2, nSettings);
            }

            _loadData($level1, settings);


        }
    })

    function _loadData(target, settings) {
        $.ajax({
            url:settings.url,
            data:settings.param,
            dataType :"jsonp",
            success:function (data) {
                _wrapSelect(target, data, settings);
            }
        });
    }

    function _wrapSelect(target, json, settings) {

        settings = $.extend({defaultLabel: "请选择", defaultValue: "", label: "label", value: "value", root: "result", selectVal: ""}, settings);

        var list = getJsonValByProperty(json, settings.root) || [];
        target.find("option").remove();
        var html = '<option value=' + settings.defaultValue + '>' + settings.defaultLabel + '</option>';
        $.each(list, function (i, o) {
            var label = getJsonValByProperty(o, settings.label);
            var value = getJsonValByProperty(o, settings.value);

            html += '<option value="' + value + '">' + label + '</option>';
        });


        target.html(html);
        target.find("option[value=" + settings.selectVal + "]").attr("selected", "selected");

        if(settings.selectVal != ""){
			if((settings.selectVal.length==1&&settings.selectVal=="1") || settings.selectVal.length>1){
	            $("#"+settings.name).val(target.find("option[value=" + settings.selectVal + "]").text());
	            $("#"+settings.name+"Id").val(target.find("option[value=" + settings.selectVal + "]").val());
			}
        }
        target.trigger("completed");

    }

    function getJsonValByProperty(json, prop) {
        if (prop.indexOf(".") != -1) {
            var props = prop.split(".");
            for (var i = 0; i < props.length; i++) {
                json = getJsonValByProperty(json, props[i]);
            }
            return json;
        } else {
            for (var key in json) {
                if (key == prop)
                    return json[key];
            }
        }
    }

})(jQuery);