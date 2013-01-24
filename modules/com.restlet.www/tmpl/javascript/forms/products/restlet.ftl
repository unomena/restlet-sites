Ext.onReady(function(){
   Ext.QuickTips.init();

   // turn on validation errors beside the field globally
   Ext.form.Field.prototype.msgTarget = 'side';

   var countryStore = new Ext.data.SimpleStore({
      fields: ['name', 'value'],
      data: [<#list country['ISO_3166-1_List_${language}']['ISO_3166-1_Entry'] as option>["${option['ISO_3166-1_Country_name']}", "${option['ISO_3166-1_Alpha-2_Code']}"]<#if option_has_next>, </#if></#list>]
   });

   var simple = new Ext.form.FormPanel({
      standardSubmit:true,
      method: 'post',
      id: 'suscribe',
      renderTo: 'product1-form',
      labelWidth: 100,
      frame:true,
      border: false,
      width: 400,

      items: [{name: 'formPostName',value: 'product1',xtype: 'hidden'}, {name: 'formPostDescription',value: 'Achat en ligne produit - étape 1',xtype: 'hidden'},
         {name: 'language',value: '${language}',xtype: 'hidden'},
         {name: 'productTransferableLicenceVersion',value: '${productTransferableLicenceVersion}',xtype: 'hidden'},
         {name: 'productNonTransferableLicenceVersion',value: '${productNonTransferableLicenceVersion}',xtype: 'hidden'},
         {xtype:'fieldset',
            title: '${labels.labels.products.licenceTitle['${language}']}',
            collapsible: false,
            autoHeight:true,
            defaults: {width: 210},
            defaultType: 'textfield',
            width: 354,
            items :[{fieldLabel: '${labels.labels.products.productName['${language}']}',name: 'coveredProductName',allowBlank:false,maxLength: 100,itemCls: 'required', autoCreate:{tag: "input", type: "text", size: "20", autocomplete: "on"}},
               {fieldLabel: '${labels.labels.products.productDescription['${language}']}',name: 'coveredProductDescription',allowBlank:false,maxLength: 200,itemCls: 'required', autoCreate:{tag: "input", type: "text", size: "20", autocomplete: "on"}},
               {fieldLabel: '${labels.labels.products.transferableLicence['${language}']}',name: 'transferableLicence',xtype: 'checkbox',inputValue: 'true',checked: false,anchor:'0%'}
            ]
         },
         {xtype:'fieldset',
            title: '${labels.labels.products.suscriberTitle['${language}']}',
            collapsible: false,
            autoHeight:true,
            defaults: {width: 210, defaultAutoCreate:{tag: "input", type: "text", size: "20", autocomplete: "on"}},
            defaultType: 'textfield',
            width: 354,
            items :[{fieldLabel: '${labels.labels.firstName['${language}']}',name: 'signerFirstName',allowBlank:false,maxLength: 100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.lastName['${language}']}',name: 'signerLastName',allowBlank:false,maxLength: 100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.function['${language}']}',name: 'signerPosition', maxLength: 100},
               {fieldLabel: '${labels.labels.email['${language}']}',name: 'signerEmail',vtype:'email',allowBlank:false,maxLength: 100,itemCls: 'required'}
            ]
         },
         {xtype:'fieldset',
            title: '${labels.labels.products.organizationTitle['${language}']}',
            collapsible: false,autoHeight:true,width: 354,
            defaults: {width: 210, defaultAutoCreate:{tag: "input", type: "text", size: "20", autocomplete: "on"}},
            defaultType: 'textfield',
            items :[{fieldLabel: '${labels.labels.organization['${language}']}',name: 'organizationName',allowBlank:false,maxLength: 100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.webSite['${language}']}',name: 'organizationWebSite',maxLength: 100},
               {fieldLabel: '${labels.labels.address1['${language}']}',name: 'organizationAddressLine1',allowBlank:false,maxLength: 100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.address2['${language}']}',name: 'organizationAddressLine2',maxLength: 100},
               {fieldLabel: '${labels.labels.zipCode['${language}']}',name: 'organizationAddressZipCode',allowBlank:false,maxLength: 10,itemCls: 'required'},
               {fieldLabel: '${labels.labels.city['${language}']}',name: 'organizationAddressCity',allowBlank:false,maxLength: 100,itemCls: 'required'},
               {fieldLabel: '${labels.labels.state['${language}']}',name: 'organizationAddressState',maxLength: 100},
               {fieldLabel: '${labels.labels.country['${language}']}',name: 'organizationAddressCountry',xtype: 'combo',editable:false,store: countryStore, displayField: 'name',typeAhead: true,triggerAction: 'all',mode: 'local',emptyText:'${labels.labels.select.country['${language}']}',allowBlank:false,maxLength: 100,itemCls: 'required'}
            ]
         },
         {xtype:'panel',
            width: 354,
            buttonAlign:'center',
            buttons:[{
               text: '${labels.labels.button.next['${language}']}',
               handler: function(){
                  if(simple.getForm().isValid()){
                     simple.getForm().getEl().dom.action = 'nrfStep1';
                     simple.getForm().getEl().dom.method = 'POST';
                     simple.getForm().submit();
                  }
               }
            }]
         }
      ]
   });
});