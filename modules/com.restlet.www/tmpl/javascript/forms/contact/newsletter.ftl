Ext.onReady(function(){
   Ext.QuickTips.init();

   // turn on validation errors beside the field globally
   Ext.form.Field.prototype.msgTarget = 'side';

   var simple = new Ext.FormPanel({
      id: 'suscribe',
      renderTo: 'suscribe-form',
      labelWidth: 100,
      frame:true,
      border: false,
      width: 400,
       
      items: [{name: 'formPostName',value: 'newsletter',xtype: 'hidden'}, {name: 'formPostDescription',value: 'Inscription Newsletter',xtype: 'hidden'},
         {xtype:'fieldset',
            title: '${labels.labels.newsletter.locationTitle['${language}']}',
            collapsible: false,
            autoHeight:true,
            defaults: {width: 210},
            defaultType: 'textfield',
            width: 354,
            items :[{fieldLabel: '${labels.labels.firstName['${language}']}',name: 'firstName',allowBlank:false,maxLength: 100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.lastName['${language}']}',name: 'lastName',allowBlank:false,maxLength: 100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.organization['${language}']}',name: 'organisation',maxLength: 100},
               {fieldLabel: '${labels.labels.webSite['${language}']}',name: 'webSite', maxLength: 100},
               {fieldLabel: '${labels.labels.function['${language}']}',name: 'function', maxLength: 100},
               {fieldLabel: '${labels.labels.email['${language}']}',name: 'email',vtype:'email',allowBlank:false,maxLength: 100,itemCls: 'required'}
            ]
         },
         {xtype:'panel',
            width: 354,
            buttonAlign:'center',
            buttons:[{
               text: '${labels.labels.button.submit['${language}']}',
               handler: function(){
                  if(simple.getForm().isValid()){
                     simple.getForm().submit({url:'/formPosts', method: 'post', headers:{accept:'application/json'},
                        success: function(form, action){
                           obj = Ext.util.JSON.decode(action.response.responseText);
                           Ext.MessageBox.show({
                              title: '${labels.labels.status['${language}']}',
                              msg: '${labels.labels.form.saved['${language}']}',
                              buttons: Ext.MessageBox.OK,
                              icon: Ext.MessageBox.INFO,
                              modal: false
                           });
                        },
                        failure: function(form, action){
                           obj = Ext.util.JSON.decode(action.response.responseText);
                           Ext.MessageBox.show({
                              title: '${labels.labels.status['${language}']}',
                              msg: '${labels.labels.form.ajaxError['${language}']}',
                              buttons: Ext.MessageBox.OK,
                              icon: Ext.MessageBox.ERROR,
                              modal: false
                           });
                        }
                     });
                  }
               }
            }]
         }
      ]
   });
});