window.addEventListener('load', function(e) {

    window.applicationCache.addEventListener('updateready', function(e) {
        if (window.applicationCache.status == window.applicationCache.UPDATEREADY) {
            // Browser downloaded a new app cache.
            // Swap it in and reload the page to get the new hotness.
            window.applicationCache.swapCache();
            if (confirm('A new version of this site is available. Load it?')) {
                window.location.reload();
            }
        } else {
            // Manifest didn't changed. Nothing new to server.
        }
    }, false);

}, false);

$(document)
    .ready(function() {
    var village_list_view_configs = {
        'page_header': 'Village',
        'backbone_collection': village_offline_collection,
        'table_template_name': 'village_table_template',
        'list_item_template_name': 'village_list_item_template',
    };
    var video_list_view_configs = {
        'page_header': 'Video',
        'backbone_collection': video_offline_collection,
        'table_template_name': 'video_table_template',
        'list_item_template_name': 'video_list_item_template'
    };
    var persongroup_list_view_configs = {
        'page_header': 'Group',
        'backbone_collection': persongroup_offline_collection,
        'table_template_name': 'persongroup_table_template',
        'list_item_template_name': 'persongroup_list_item_template'
    };
    var screening_list_view_configs = {
        'page_header': 'Screening',
        'backbone_collection': screening_offline_collection,
        'table_template_name': 'screening_table_template',
        'list_item_template_name': 'screening_list_item_template',
    };
    var person_list_view_configs = {
        'page_header': 'Person',
        'backbone_collection': person_offline_collection,
        'table_template_name': 'person_table_template',
        'list_item_template_name': 'person_list_item_template',
        'add_edit_template_name': 'person_add_edit_template'
    };
    var personadoptvideo_list_view_configs = {
        'page_header': 'Adoption',
        'backbone_collection': personadoptvideo_offline_collection,
        'table_template_name': 'personadoptvideo_table_template',
        'list_item_template_name': 'personadoptvideo_list_item_template'
    };
    var animator_list_view_configs = {
        'page_header': 'Animator',
        'backbone_collection': animator_offline_collection,
        'table_template_name': 'animator_table_template',
        'list_item_template_name': 'animator_list_item_template'
    };

    var notifications_view = Backbone.View.extend({
        el: '#notifications'
    });
    var list_item_view = Backbone.View.extend({
        tagName: 'tr',
        events: {
            "click a.edit": "edit",
            "click a.destroy": "remove"
        },

        initialize: function(params) {
            this.template = _.template($('#' + params.view_configs.list_item_template_name)
                .html());
            this.error_notif_template = _.template($('#' + 'error_notifcation_template')
                .html());
            this.success_notif_template = _.template($('#' + 'success_notifcation_template')
                .html());

        },

        edit: function(event) {
            event.preventDefault();
            event.stopImmediatePropagation();
            appRouter.navigate('person/edit/' + this.model.id, true);

        },

        remove: function(event) {
            event.stopImmediatePropagation();
            event.preventDefault();
            var context = this;
            if (confirm("Are you sure you want to delete this entry?")) {
                this.model.destroy({
                    error: function() {
                        console.log("error deleting a model");
                        $(notifs_view.el)
                            .append(context.error_notif_template({
                            msg: "Failed to Delete the " + context.options.view_configs.page_header
                        }));


                    },
                    success: function() {
                        console.log("deleted a model");
                        $(notifs_view.el)
                            .append(context.success_notif_template({
                            msg: "Deleted the " + context.options.view_configs.page_header
                        }));

                    }
                });
            }


        },

        render: function() {
            $(this.el)
                .html(this.template(this.model.toJSON()));
            return this;
        }
    });

    var list_view = Backbone.View.extend({

        events: {
            "click button#add": "addNew",

        },

        initialize: function(view_configs) {
            this.view_configs = view_configs;
            this.collection = new view_configs.backbone_collection();
            this.table_template_name = view_configs.table_template_name;
            console.log("template_name : " + this.table_template_name)
            this.template = _.template($('#' + 'list_view_template')
                .html());
            this.table_template = _.template($('#' + this.table_template_name)
                .html());
            this.collection.bind('all', this.render_data, this);
            this.datatable = null;

        },

        render: function(show_heading) {
            $(this.el)
                .html(this.template({
                header_name: show_heading
            }));
            $(this.el)
                .append(this.table_template());
            this.collection.fetch();


            return this;
        },
        render_data: function() {
            if (this.datatable) {
                this.datatable.fnDestroy();
            }
            $tbody = this.$("tbody");
            $tbody.html('');
            console.log("in render_data...change in collection...rendering list view");

            this.collection.each(function(model) {
                $tbody.append(new list_item_view({
                    model: model,
                    view_configs: this.view_configs
                })
                    .render()
                    .el);
            }, this);
            this.datatable = this.$('table')
                .dataTable({
                "bDestroy": true
            });
        },
        addNew: function() {
            appRouter.navigate('person/add', true);
        },



    });

    var person_add_edit_view = Backbone.View.extend({

        events: {
            'click #save': 'setjustsave',
            'click #save_add_another': 'set_save_add_another'
        },
        setjustsave: function() {
            console.log("ADD/EDIT: only save");
            this.just_save = true;
            this.save_and_add_another = false;
        },
        set_save_add_another: function() {
            console.log("ADD/EDIT: save and add another");
            this.save_and_add_another = true;
            this.just_save = false;
        },
        error_notif_template: _.template($('#' + 'error_notifcation_template')
            .html()),
        success_notif_template: _.template($('#' + 'success_notifcation_template')
            .html()),

        initialize: function(params) {
            console.log("ADD/EDIT: params to add/edit view:");
            console.log(params);

            this.person_offline_model = new person_offline_model();
            this.person_online_model = new person_online_model();
            this.view_configs = params.view_configs;

            // model = null;
            json = null;
            if (params.model_id) {

                // model = new person_offline_model({
                //                 id: params.model_id
                //             });
                this.person_offline_model.set({
                    id: params.model_id
                });
                _(this)
                    .bindAll('fill_form');
                this.person_offline_model.bind('change', this.fill_form);
                this.edit_case = true;

            } else this.edit_case = false;

            this.add_edit_template = _.template($('#' + this.view_configs.add_edit_template_name)
                .html());
            options_inner_template = _.template($('#options_template')
                .html());

            this.villages = new village_offline_collection();
            this.persongroups = new persongroup_offline_collection();
            _(this)
                .bindAll('render_villages');
            _(this)
                .bindAll('render_persongroups');

            this.villages.bind('all', this.render_villages);

            this.persongroups.bind('all', this.render_persongroups);

            this.just_save = false;
            this.save_and_add_another = false;
            _(this)
                .bindAll('save');

        },
        render: function() {
            // put the add_edit template in dom and call validate plugin on the form

            $(this.el)
                .html(this.add_edit_template());
            this.villages.fetch({
                success: function() {
                    console.log("ADD/EDIT: Village coll fetched");
                },
                error: function() {
                    //ToDO: error handling
                    console.log("ERROR: ADD/EDIT: Village collection could not be fetched!");
                    alert("ERROR: ADD/EDIT: Village collection could not be fetched!");
                }
            });

            this.persongroups.fetch({
                success: function() {
                    console.log("ADD/EDIT: PersonGroup coll fetched");
                },
                error: function() {
                    //ToDO: error handling
                    console.log("ERROR: ADD/EDIT: PersonGroup collection could not be fetched!");
                    alert("ERROR: ADD/EDIT: PersonGroup collection could not be fetched!");
                }
            });

            if (this.edit_case == true) {

                this.person_offline_model.fetch({
                    success: function() {
                        console.log("EDIT: edit model fetched");
                    },
                    error: function() {
                        //ToDO: error handling
                        console.log("ERROR: EDIT: Edit model could not be fetched!");
                        alert("ERROR: EDIT: Edit model could not be fetched!");
                    }
                    //ToDO: error handling
                });
            }

            var context = this;
            this.$('form')
                .validate({
                submitHandler: function() {
                    context.save();
                },
                highlight: function(element, errorClass, validClass) {
                    $(element)
                        .parent('div')
                        .parent('div')
                        .addClass("error");

                },
                unhighlight: function(element, errorClass, validClass) {
                    $(element)
                        .parent('div')
                        .parent('div')
                        .removeClass("error");

                },
                errorElement: "span",
                errorClass: "help-inline"
            });

            return this;
        },

        fill_form: function() {
            //render must be finished before this func
            console.log("EDIT: its edit case, model for edit:");
            console.log(this.person_offline_model);
            json = this.person_offline_model.toJSON();
            json.village = json.village.id;
            json.group = json.group.id;
            Backbone.Syphon.deserialize(this, json);
        },
        save: function() {
            // read data from form
            // check internet conn
            //if internect conn create on online coll/model(?) then create on offline coll/model        (server log ?)
            //else create on offline coll/model, add to local log
            var form_data = Backbone.Syphon.serialize(this);
            if (form_data.hasOwnProperty('')) {
                delete form_data[''];
            }

            var village = this.villages.where({
                id: form_data['village']
            })[0];
            var persongroup = this.persongroups.where({
                id: form_data['group']
            })[0];
            // var offline_data = new Object();
            var offline_data = jQuery.extend(true, {}, form_data);
            var online_data = form_data;
            console.log(JSON.stringify(form_data));

            if (village) {
                offline_data['village'] = {
                    'id': village.get('id'),
                    'village_name': village.get('village_name')
                };
            } else offline_data['village'] = {
                'id': null,
                'village_name': null
            };

            if (persongroup) {
                offline_data['group'] = {
                    'id': persongroup.get('id'),
                    'group_name': persongroup.get('group_name')
                };
            } else offline_data['group'] = {
                'id': null,
                'group_name': null
            };

            var context = this;
            if (this.edit_case) {
                this.person_offline_model.set(offline_data);
                console.log("editing person to:");
                console.log(JSON.stringify(this.person_offline_model));
                this.person_offline_model.save(null, {
                    error: function() {
                        console.log("EDIT: error while saving edit of person in local db");
                        $(notifs_view.el)
                            .append(context.error_notif_template({
                            msg: "Failed to Edit the person"
                        }));


                    },
                    success: function() {
                        console.log("EDIT: successfully edited a person in local db");
                        $(notifs_view.el)
                            .append(context.success_notif_template({
                            msg: "Successfully Edited the person"
                        }));

                    }
                });

            } else {
                this.person_offline_model.set(offline_data);
                this.person_online_model.set(online_data);
                console.log("ADD: adding new person:");
                var context = this;

                this.person_online_model.save(null, {
                    error: function() {
                        console.log("ADD: error adding person online");
                        $(notifs_view.el)
                            .append(context.error_notif_template({
                            msg: "Failed to Add the person on server"
                        }));


                    },
                    success: function(model) {
                        console.log("ADD: successfully added a person on server");
                        $(notifs_view.el)
                            .append(context.success_notif_template({
                            msg: "Successfully Added the person on server"
                        }));
                        console.log(model.toJSON());
                        console.log(model.id);


                        context.person_offline_model.set({
                            id: model.id
                        });
                        console.log("ADD: saving this locally" + JSON.stringify(context.person_offline_model));

                        context.person_offline_model.save(null, {
                            error: function() {
                                console.log("ADD: error adding person locally");
                                $(notifs_view.el)
                                    .append(context.error_notif_template({
                                    msg: "Failed to Add the person locally"
                                }));


                            },
                            success: function(model) {
                                console.log("ADD: successfully added a person locally");
                                $(notifs_view.el)
                                    .append(context.success_notif_template({
                                    msg: "Successfully Added the person locally"
                                }));
                                console.log(model.toJSON());

                            }
                        });

                    }
                });

            }

            if (this.just_save) appRouter.navigate('person', true);
            else if (this.save_and_add_another) {
                appRouter.navigate('person/add');
                appRouter.addPerson(); //since may be already on the add page, therefore have to call this explicitly
            } else console.log("Bug: ADD/Edit after save option not set!");


        },

        render_villages: function() {
            $village_list = this.$('#id_village');
            this.villages.each(function(village) {
                $village_list.append(options_inner_template({
                    id: village.get("id"),
                    name: village.get("village_name")
                }));
            });
            if (json) {
                console.log("ADD/EDIT: syphoning village");
                Backbone.Syphon.deserialize(this, json);
            }

        },
        render_persongroups: function() {
            $persongroup_list = this.$('#id_group');
            $persongroup_list.html("<option value='' selected='selected'>---------</option> ");
            this.persongroups.each(function(pg) {
                $persongroup_list.append(options_inner_template({
                    id: pg.get("id"),
                    name: pg.get("group_name")
                }));
            });
            if (json) {
                console.log("ADD/EDIT: syphoning persongroup");
                Backbone.Syphon.deserialize(this, json);
            }
        }

    });

    var notifs_view = new notifications_view;
    
   


});