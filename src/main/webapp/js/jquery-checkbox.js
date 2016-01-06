/**
 * 使用自定checkbox替代
 */
(function ($) {
    $.fn.checkBox = function (options, callback) {
        if ($.type(options) === 'string') {
            $.fn.checkBox.setAct(options, $(this), callback)
            return;
        }
        $.fn.checkBox.init($(this), options);
    };

    $.extend($.fn.checkBox, {
        defaults: {
            classes: 'jq_checkbox', //样式名
            event: true, //是否使用默认点击事件
            active: false, //选中后的回调
            cancel: false, //取消点击后的回调
            tmpl: '<a href="javascript:;"></a>' //模板
        },
        queue: [],
        init: function (doms, options) {
            this.options = $.extend({}, this.defaults, options);
            this.queue.push(this.options);
            this.replace(doms);
        },
        replace: function (doms) {
            var self = this,
                ops = self.options;
            var checkbox = $(ops.tmpl).addClass(ops.classes).attr('checkbox', self.queue.length - 1);
            doms.each(function () {
                var newCheckbox = checkbox.clone().data('checkbox', ops);
                newCheckbox.addClass($(this).attr('class'));
                if ($(this).is('[disabled]')) {
                    newCheckbox.addClass(ops.classes + '_disabled');
                } else {
                    ops.event && self.bindEvent($(this), newCheckbox);
                }
                if ($(this).is(':checked')) {
                    newCheckbox.addClass(ops.classes + '_active')
                }
                if ($(this).next('[checkbox]')) {
                    $(this).next('[checkbox]').remove();
                }
                $(this).hide().after(newCheckbox);
            });
        },
        bindEvent: function (checkbox, newCheckbox) {
            var self = this,
                ops = self.options;
            newCheckbox.hover(function () {
                $(this).addClass(ops.classes + '_hover');
            }, function () {
                $(this).removeClass(ops.classes + '_hover');
            });
            newCheckbox.on('click', function () {
                if (!$(this).parents('label').size() || $(this).is('a')) {
                    if ($(this).hasClass(ops.classes + '_active') || $(this).hasClass(ops.classes + '_half')) {
                        self.setAct('cancel', checkbox);
                    } else {
                        self.setAct('active', checkbox);
                    }
                }
            });
            checkbox.on('click', function () {
                if ($(this).is(':checked')) {
                    self.setAct('active', $(this));
                } else {
                    self.setAct('cancel', $(this));
                }
            });
        },
        setAct: function (act, doms, callback) {
            if ($.inArray(act, ['active', 'cancel', 'half']) < 0) {
                return;
            }
            var self = this;
            doms.each(function () {
                if ($(this).is('[disabled]')) {
                    return;
                }
                var newCheckbox = $(this).next('[checkbox]');
                if (!newCheckbox.length) {
                    return;
                }
                var options = self.queue[newCheckbox.attr('checkbox')],
                    classes = options.classes;

                if (callback === undefined && options) {
                    switch (act) {
                        case 'active':
                        case 'half':
                            callback = options.active;
                            break;
                        case 'cancel':
                            callback = options.cancel;
                            break;
                    }
                }
                if (newCheckbox.length && !newCheckbox.hasClass(classes + '_disabled')) {
                    $.isFunction(callback) && callback($(this), newCheckbox, act == 'half');
                    switch (act) {
                        case 'active':
                            this.checked = true;
                            newCheckbox.removeClass(classes + '_half').addClass(classes + '_active');
                            break;
                        case 'cancel':
                            this.checked = false;
                            newCheckbox.removeClass(classes + '_active').removeClass(classes + '_half');
                            break;
                        case 'half':
                            this.checked = true;
                            newCheckbox.removeClass(classes + '_active').addClass(classes + '_half');
                            break;
                        default :
                    }
                }
            });
        }
    });
})(jQuery);