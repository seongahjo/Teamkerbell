+function ( $, window) {
  
  // mugine
  var Mugine = (function(){var e=document,H=window,r="div",g="wrap",f="push",v="join",m="data",x="split",o="object",K="string",O="replace",F="element",u="nodeType",L="innerHTML",s="attributes",k="childNodes",l="parentNode",y="firstChild",t="appendChild",G="insertBefore",E="replaceChild",z="setAttribute",N="createElement",a="data-variable",d="querySelectorAll",J="createDocumentFragment",C={start:"#{",end:"}",buffer:""},c={start:"<%",end:"%>",buffer:"="},n=(function(){var P={};return function(W,U,S){S=S||{};for(var R in A){U["_"+R]=(function(X,Y){return function(){X._data=Y;var Z=X.apply(this,arguments);delete X._data;return Z}})(A[R],U)}var V=S.tokens||c;var T=P[W]=P[W]||new Function("obj","var p=[],print=function(){p.push.apply(p,arguments);};with(obj){p.push('"+W[O](/[\r\t\n]/g," ")[x](V.start)[v]("\t")[O](new RegExp("\\(\\(\\^\\|"+V.end+"\\)\\[\\^\t\\]\\*\\)","g"),"$1\r")[O](new RegExp("\t"+V.buffer+"(.*?)"+V.end,"g"),"',$1,'")[x]("\t")[v]("');")[x](V.end)[v]("p.push('")[x]("\r")[v]("\\'")+"');} p = p.filter(function(v) {return typeof v == 'string' ? v.replace(/^s+|s+$/g, '').length : true});for (var i = 0; i < p.length; i++) { var o = p[i]; if (o && o.outerHTML) p[i] = o.outerHTML; }return p.length > 1 ? p.join('') : typeof p[0] == 'undefined' ? '' : p[0];");var Q=U?T(U):T;for(var R in A){delete U["_"+R]}return Q}})();function q(ad,U,ae){ae=ae||{};var S=i(U);for(var R in S){var ac=S[R];ac[u]?ac[z](a,R):null}var V=n(ad,U,ae);var af=h(V);var Y=af[d]("*["+a+"]");var Y=af.querySelectorAll("*["+a+"]");for(var T=0,ab;ab=Y[T];T++){var P=ab.getAttribute(a),ac=S[P];if(ac){if(ac[u]){for(var Z=0,W;W=ab[s][Z];Z++){ac[z](W.nodeName,W.nodeValue)}if(!ac[k].length){for(var X=0,Q=ab[k];X<Q.length;X++){ac[t](Q[X--])}}ab[l][E](ac,ab)}else{if(typeof elem!="object"){var aa=ac;ac=ab;ac[L]=aa}}}}for(var R in S){var ac=S[R];ac[u]?ac.removeAttribute(a):null}return af}var A={build:function(){return D.call(this,arguments[0],arguments[1],arguments.callee._data)}};function D(Q,U,P){var X=null,P=P||{};if(typeof U=="string"){X=q(U,P,{helpers:A})}else{if(U instanceof Array){X=Q=Q||e[J]();for(var R=0,W;W=U[R];R++){Q[t](D.call(this,null,W,P))}}else{if(typeof U=="object"){var S=Q||U[F];X=(function(){if(typeof S=="string"){var Y=q(S,P,{helpers:A,tokens:C})[k];if(Y.length==1&&Y[0][u]==1){return Y[0]}else{throw ("property 'element' must evaluate to a single dom element")}}else{if(typeof S=="object"&&S[u]){return S}}return null})()||e[N](U.nodeName||U.tagName||r);Q=X;var V=U[g];for(var T in U[s]){Q[z](b(T),n(U[s][T],P))}if(U[k]){D.call(this,Q,U[k],P)}j(Q,U,P);if(V){X=Q=I(Q,D.call(this,null,V,P))}}}}return X}var p=[];function B(R){for(var Q=0,T;T=p[Q];Q++){if(T[F]==R){p.splice(Q--,1);for(var P=0,S;S=T.markup[P];P++){S[l][E](R,S)}break}}}function w(U,X,T){T=T||{};B.call(this,U);var V=U.cloneNode(),W=U[l],R=V;W?W[G](V,U):null;var Z=X[k];if(typeof X=="object"&&!(X instanceof Array)&&(!Z||Z.length==1)){X[F]=U}T[F]=T[F]||U;var aa=D.call(this,null,X,T);var Q=[],Y=aa[k];for(var S=0,P;P=Y[S];S++){Q[f](P)}p[f]({element:U,markup:Q});if(R){R[l][E](aa,R)}return aa}function M(){}M.prototype={render:function(Q,R,U){var T=typeof Q==K?e[d](Q):Q instanceof Array?Q:[Q];for(var P=0,S;S=T[P];P++){w.call(this,S,R,U)}return T},clean:function(P){B.call(this,P)},helpers:A};function b(P){return P[O](/\W+/g,"-")[O](/([a-z\d])([A-Z])/g,"$1-$2")}function h(Q){if(typeof Q!="string"){return Q}var S,R=e[N](r),P=e[J]();R[L]=Q;while(S=R[y]){P[t](S)}return P}function j(Q,R,T){for(var P in R){if(!!([F,s,m,k,g].indexOf(P)+1)){continue}var S=R[P];typeof S==o&&!S[u]?arguments.callee(Q[P]||{},S,T):Q[P]=typeof S==K?n(S,T,{helpers:A,tokens:S.indexOf(c.start)>=0?c:C}):S}return Q}function I(P,S){if(!S){return P}var Q=P[l],R=P.nextSibling;S=S[u]===11?S[y]:S;S[t](P);if(Q){Q[G](S,R)}return S}function i(T,S,P){S=S||"",P=P||{};for(var R in T){var Q=T[R],U=S?S+"["+R+"]":R;if(typeof Q=="undefined"||Q==null){continue}P[U]=Q;if(typeof Q==o&&!Q.outerHTML){i(Q,U,P)}}return P}if($=jQuery){$.fn.render=$.fn.render||function(P,Q){return this.each(function(){H.mugine.render(this,P,Q);return $(this)})}}H.Mugine=H.Mugine||M;H.mugine=H.mugine||new M();return M})();
  
  var $window = $(window);
  var pluginName = 'filepicker';
  
  var defaults = {
    style: 'auto', 
    renderUI: null, 
    renderThumbnail: null
  };
  
  var dataOptions = ['label'];
      
  function getFilename(url) {
    var match = /\/([^\\\/:*?\"<>|]+)$/i.exec(url);
    if (match) return match[1];
    return null;
  }
  
  function getFileExtension(url) {
    var match = /\.([0-9a-z]+)(?:[\?#]|$)/i.exec(url);
    if (match) return match[1];
    return null;
  }
  
  function Filepicker(element, options) {
    
    var $element = $(element);
    
    var instance = this;
    var files = [];
    var mugine = new Mugine();
    
    var $preview = $('<div></div>'), preview = $preview.get(0);
    var $input = $('<input/>'), input = $input.get(0);
    var $button = $('<button type="button"></button>'), button = $button.get(0);
      
    var style = typeof options.style == 'string' ? $.fn.filepicker.getStyle(
      options.style == "auto" ? 
      // detect styles
      isBootstrap() ? 'bootstrap' : 
      isJQueryUI() ? 'jquery-ui' : 
      'default'
      // option value
       : options.style
    ) : options.style;
    
    $window
      .on('resize', function(e) {
        layout.call(instance);
      });
      
    $element
      .on('change', function(e) {
        handleFileSelect(e);
      });
      
    $button
      .on('click', function() {
        $element.trigger('click');
      });
    
    $input
      .on('click', function() {
        $element.trigger('click');
      })
      .on('focus', function() {
        $(this).blur();
      });
    
    function renderUI() {

      var renderStyle = true;
      
      if (typeof options.renderUI == "function") {
        var retVal = options.renderUI.call(this, element, button, input, preview);
        if (typeof retVal != 'undefined') {
          renderStyle = retVal;
        }
      }
      if (renderStyle) {
        // render
        var result = mugine.render(element, style.ui, {
          button: button, 
          input: input, 
          preview: preview
        });
      }
    }
    
    function changed() {
      // changed
    }
    
    function update() {
      // filenames
      var fileNames = $(files).map(function() {
        return this.name;
      }).get();
      $(input).prop('value', fileNames.join(", "));
    }
    
    function renderPreview() {

      var instance = this;
      
      $preview.html("");
      
      // Loop through the FileList and render image files as thumbnails.
      $(files).each(function() {
        if (!this.type.match('image.*')) {
          return;
        }
        var $thumbnail = $('<img src="' + this.src + '" title="' + this.name + '" style="max-width: 100%; height: auto"/>');
        $preview.append($thumbnail);
        
        var renderStyle = true;
    
        if (typeof options.renderThumbnail == "function") {
          var retVal = options.renderThumbnail.call(instance, $thumbnail[0], this);
          if (typeof retVal != 'undefined') {
            renderStyle = retVal;
          }
        }
        
        if (renderStyle) {
          mugine.render($thumbnail.get(0), style.thumbnail, this);
        }
          
      });
      
    }
    
    function layout() {
      $element.css({
        position: 'absolute', 
        visibility: 'hidden' 
      });
      if (typeof options.resize == "function") {
        options.resize.call(this);
      }
    }

    
    function handleFileSelect(evt) {
      files = evt.target.files; // FileList object
      
      for (var i = 0, file; file = files[i]; i++) {
  
        // Only process image files.
        if (!file.type.match('image.*')) {
          continue;
        }
  
        var reader = new FileReader();
  
        // Closure to capture the file information.
        reader.onload = (function(file) {
          return function(e) {
            file.src = e.target.result;
            renderPreview();
          };
        })(file);
  
        // Read in the image file as a data URL.
        reader.readAsDataURL(file);

      }
      
      update.call(instance);
    }
    
    function isBootstrap() {
      return $('<div></div>').addClass('pull-right').css('float') == "right";
    }
    
    function isJQueryUI() {
      return $('<div></div>').addClass('ui-front').css('z-index') > 1;
    }
  
    function init() {
    
      var value = $element.attr('value');
      var values = value ? value.split(",") : [];

      files = $(values).map(function() {
        var filename = getFilename(this);
        var fileExtension = getFileExtension(this);
        var type = "";
        if ($.inArray(fileExtension, ['jpg', 'jpeg', 'png', 'gif']) >= 0) {
          // image
          type = "image/" + fileExtension;
        }
        return {name: filename, type: type, src: this};
      }).get();
      
      renderUI.call(this);
      renderPreview.call(this);
      layout.call(this);
      update.call(this);
      
    }
    
    
    
    init.call(this);
    
    
  }
  
  var pluginClass = Filepicker;

  // register plugin
  $.fn[pluginName] = function(options) {
    options = $.extend({}, defaults, options);
    return this.each(function() {
      if (!$(this).data(pluginName)) {
          $(this).data(pluginName, new pluginClass(this, options));
      }
      return $(this);
    });
  };
  
  // default styles
  var _styles = {
    'default': {
      ui: [
        { element: '#{ preview }', className: 'filepicker-preview'}, 
        { className: 'filepicker-ui', childNodes: [{ element: '#{ button }', innerHTML: '<%= element.getAttribute("data-label") %>' }, { element: '#{ input }' }]},  
        { element: '#{ element }' }
      ], 
      thumbnail: {
        wrap: '<div style="margin-bottom: 20px; text-align: center; border-radius: 5px"></div>', 
        style: "max-width: 100%"
      }
    }, 
    'bootstrap': {
      ui: [
        { element: '#{ preview }' }, 
        {
          className: 'input-group', 
          childNodes: [{
            element: '#{ button }', 
            className: 'btn btn-default', 
            wrap: '<div class="input-group-btn"></div>', 
            innerHTML: '<i class="glyphicon glyphicon-upload"></i> <%= element.getAttribute("data-label") %>'
          }, {
            element: '#{ input }',  
            className: 'form-control', 
            placeholder: '#{ element.placeholder }'
          }]
        }, { element: '#{ element }' }
      ], 
      thumbnail: {
        wrap: '<div class="thumbnail"></div>'
      }
    }, 
    'jquery-ui': {
      ui: [{
        element: '#{ preview }'
      }, {
        className: 'ui-buttonset', 
        childNodes: [
          {
            element: '#{ button }',  
            className: 'ui-button ui-state-default ui-corner-left <% if (!element.getAttribute("data-label")) { %> ui-button-icon-only<% } else { %> ui-button-text-icon-primary<% } %>', 
            innerHTML: '<span class="ui-button-icon-primary ui-icon  ui-icon-document"></span><span class="ui-button-text"><%= element.getAttribute("data-label") || "&nbsp;" %></span>'
          }, 
          {
            element: '#{ input }',  
            className: 'ui-button ui-state-default ui-corner-right', 
            placeholder: '#{ element.placeholder }'
          }, 
          { 
            element: '#{ element }' 
          }
        ]
      }], 
      thumbnail: {
        wrap: '<div style="padding: 5px; margin-bottom: 20px; text-align: center" class="ui-widget ui-widget-content ui-helper-clearfix ui-corner-all"></div>', 
        style: {
          maxWidth: '100%'
        }
      }
    }
  };
  
  $.fn[pluginName].registerStyle = function(name, json) {
    _styles[name] = json;
  };
  
  $.fn[pluginName].getStyle = function(name) {
    return _styles[name];
  };
  
  

}( jQuery, window );