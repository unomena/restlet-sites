Ext.onReady(function(){
   Ext.QuickTips.init();

   // turn on validation errors beside the field globally
   Ext.form.Field.prototype.msgTarget = 'side';

   var skillsStore = new Ext.data.SimpleStore({
      fields: ['name', 'value'],
      data: [<#list labels.labels.consulting.comboBox.skills.option as option>['${option['${language}']}', '${option['${language}']}']<#if option_has_next>, </#if></#list>]
   });
   var estimatedDurationStore = new Ext.data.SimpleStore({
      fields: ['name', 'value'],
      data: [<#list labels.labels.development.comboBox.estimatedDuration.option as option>['${option['${language}']}', '${option['${language}']}']<#if option_has_next>, </#if></#list>]
   });
   var durationTypeStore = new Ext.data.SimpleStore({
      fields: ['name', 'value'],
      data: [<#list labels.labels.development.comboBox.durationType.option as option>['${option['${language}']}', '${option['${language}']}']<#if option_has_next>, </#if></#list>]
   });
   var localizationStore = new Ext.data.SimpleStore({
      fields: ['name', 'value'],
      data: [<#list labels.labels.development.comboBox.localization.option as option>['${option['${language}']}', '${option['${language}']}']<#if option_has_next>, </#if></#list>]
   });
   var countryStore = new Ext.data.SimpleStore({
      fields: ['name', 'value'],
      data: [<#list country['ISO_3166-1_List_${language}']['ISO_3166-1_Entry'] as option>["${option['ISO_3166-1_Country_name']}", "${option['ISO_3166-1_Alpha-2_Code']}"]<#if option_has_next>, </#if></#list>]
   });
   
   var simple = new Ext.FormPanel({
      id: 'suscribe',
      renderTo: 'consulting-form',
      labelWidth: 100,
      frame:true,
      border: false,
      width: 400,

      items: [{name: 'formPostName',value: 'consulting',xtype: 'hidden'}, {name: 'formPostDescription',value: 'Prise de contact - conseil',xtype: 'hidden'},
         {xtype:'fieldset',
            title: '${labels.labels.consulting.missionTitle['${language}']}',
            collapsible: false,
            autoHeight:true,
            defaults: {width: 210},
            defaultType: 'textfield',
            width: 354,
            items :[{fieldLabel: '${labels.labels.consulting.type['${language}']}',name: 'type',allowBlank:false,maxLength:100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.consulting.skillsType['${language}']}',name: 'skillsType',xtype: 'combo',editable:false,store: skillsStore,displayField: 'name', typeAhead: true,triggerAction: 'all',mode: 'local',emptyText:'${labels.labels.select.skillsType['${language}']}',allowBlank:false,itemCls: 'required'},
               {fieldLabel: '${labels.labels.consulting.estimatedDuration['${language}']}',name: 'estimatedDuration',xtype: 'combo',editable:false,store: estimatedDurationStore,displayField: 'name', typeAhead: true,triggerAction: 'all',mode: 'local',emptyText:'${labels.labels.select.estimatedDuration['${language}']}',allowBlank:false,itemCls: 'required'},
               {fieldLabel: '${labels.labels.consulting.durationType['${language}']}',name: 'durationType',xtype: 'combo',editable:false,store: durationTypeStore,displayField: 'name',typeAhead: true,triggerAction: 'all',mode: 'local',emptyText:'${labels.labels.select.durationType['${language}']}',allowBlank:false,allowBlank:false,itemCls: 'required'},
               {fieldLabel: '${labels.labels.consulting.estimatedDate['${language}']}',name: 'estimatedDate',xtype:'datefield',allowBlank:false,itemCls: 'required'},
               {fieldLabel: '${labels.labels.consulting.localization['${language}']}',name: 'localization',xtype: 'combo',editable:false,store: localizationStore,displayField: 'name', typeAhead: true,triggerAction: 'all',mode: 'local',emptyText:'${labels.labels.select.localization['${language}']}',allowBlank:false,allowBlank:false,itemCls: 'required'},
               {fieldLabel: '${labels.labels.comments['${language}']}',name: 'comments',xtype: 'textarea',maxLength:1000}
            ]
         },
         {xtype:'fieldset',
            title: '${labels.labels.consulting.dataTitle['${language}']}',
            collapsible: false,
            defaults: {width: 210},
            defaultType: 'textfield',
            autoHeight:true,
            width: 354,
            items :[{fieldLabel: '${labels.labels.firstName['${language}']}',name: 'firstName',allowBlank:false,maxLength:100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.lastName['${language}']}',name: 'lastName',allowBlank:false,maxLength:100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.function['${language}']}',name: 'function',maxLength:100},
               {fieldLabel: '${labels.labels.email['${language}']}',name: 'email',vtype:'email',allowBlank:false,maxLength:100,itemCls: 'required'}]
         },
         {xtype:'fieldset',
            title: '${labels.labels.consulting.organizationTitle['${language}']}',
            collapsible: false,
            autoHeight:true,
            defaults: {width: 210},
            defaultType: 'textfield',
            width: 354,
            items :[{fieldLabel: '${labels.labels.organization['${language}']}',name: 'organisation',allowBlank:false,maxLength:100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.webSite['${language}']}',name: 'webSite',maxLength:100},
               {fieldLabel: '${labels.labels.address1['${language}']}',name: 'address1',allowBlank:false,maxLength:100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.address2['${language}']}',name: 'address2',maxLength:100},
               {fieldLabel: '${labels.labels.zipCode['${language}']}',name: 'zipCode',allowBlank:false,maxLength:100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.city['${language}']}',name: 'city',allowBlank:false,maxLength:100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.state['${language}']}',name: 'organizationAddressState',maxLength: 100},
               {fieldLabel: '${labels.labels.country['${language}']}',name: 'country',xtype: 'combo',editable:false,store: countryStore, displayField: 'name',typeAhead: true,triggerAction: 'all',mode: 'local',emptyText:'${labels.labels.select.country['${language}']}',allowBlank:false,maxLength:100,itemCls: 'required'}
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