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
        'page_header': 'Villages',
        'backbone_collection': village_offline_collection,
        'table_template_name': 'village_table_template',
        'list_item_template_name': 'village_list_item_template',
    };
    var video_list_view_configs = {
        'page_header': 'Videos',
        'backbone_collection': video_offline_collection,
        'table_template_name': 'video_table_template',
        'list_item_template_name': 'video_list_item_template'
    };
    var persongroup_list_view_configs = {
        'page_header': 'Groups',
        'backbone_collection': persongroup_offline_collection,
        'table_template_name': 'persongroup_table_template',
        'list_item_template_name': 'persongroup_list_item_template'
    };
    var screening_list_view_configs = {
        'page_header': 'Screenings',
        'backbone_collection': screening_offline_collection,
        'table_template_name': 'screening_table_template',
        'list_item_template_name': 'screening_list_item_template',
    };
    var person_list_view_configs = {
        'page_header': 'Persons',
        'backbone_collection': person_offline_collection,
        'table_template_name': 'person_table_template',
        'list_item_template_name': 'person_list_item_template',
        'add_edit_template_name': 'person_add_edit_template'
    };
    var personadoptvideo_list_view_configs = {
        'page_header': 'Adoptions',
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

    var HeaderView = Backbone.View.extend({
        // How many things to upload, button to go to the upload page
        // How many things to download, button to download
        // Name of the Current Page/View or maybe a breadcrumb
    })

    // UploadDownloadView
    // DashboardView
    // ListView -> search, add, table, sort
    // ListItemView -> add
    // AddEditView / AddView and EditView 


    // set up the view for a country
    var list_item_view = Backbone.View.extend({
        tagName: 'tr',
        events: {
            "click a.edit": "edit",
            "click a.destroy": "remove"
        },

        initialize: function(params) {
            this.template = _.template($('#' + params.view_configs.list_item_template_name)
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
            if (confirm("Are you sure you want to delete this entry?")) {
                this.model.remove();
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
            "click button#search_button": "search",
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
            this.collection.fetch();
        },

        render: function(show_heading) {
            $(this.el)
                .html(this.template({header_name: show_heading}));
            $(this.el)
                .append(this.table_template());
            
            
            return this;
        },
        render_data: function(){
            $tbody = this.$("tbody");
            console.log("rendering list view");
            this.collection.each(function(model) {
                $tbody.append(new list_item_view({
                    model: model,
                    view_configs: this.view_configs
                })
                    .render()
                    .el);
            }, this);
            this.$('table').dataTable();
        },
        addNew: function() {
            appRouter.navigate('person/add', true);
        },

        search: function() {

        }

    });


    var person_add_edit_view = Backbone.View.extend({

        events: {
           // 'click #save': 'save'
        },

        initialize: function(params) {
            this.person_offline_model = new person_offline_model();
            console.log("params to add/edit view:");
            console.log(params);
            this.view_configs = params.view_configs;
            model = null;
            json = null;
            if (params.model_id) {
                model = new person_offline_model({
                    id: params.model_id
                })
                _(this)
                    .bindAll('fill_form');

                model.bind('change', this.fill_form);
                model.fetch({
                success: function() {
                    console.log(" edit model fetched");

                }
                //ToDO: error handling
                });
                
            }
            this.add_edit_template_name = this.view_configs.add_edit_template_name;
            this.add_edit_template = _.template($('#' + this.add_edit_template_name)
                .html());
            options_inner_template = _.template($('#options_template')
                .html());
           
            this.villages = new village_offline_collection();
            this.persongroups = new persongroup_offline_collection();
            _(this)
                .bindAll('render_villages');
            _(this)
                .bindAll('render_persongroups');
            _(this)
                    .bindAll('save');
            
            this.villages.bind('all', this.render_villages);
            this.villages.fetch({
                success: function() {
                    console.log("villages coll fetched");

                }
                //ToDO: error handling
            });
            this.persongroups.bind('all', this.render_persongroups);
            this.persongroups.fetch({
                success: function() {
                    console.log("persongroups coll fetched");

                }
                //ToDO: error handling
            });
        },
        fill_form: function() {
            //render should have been called before this func
            console.log("its edit case, model for edit:");
            console.log(model);
            json = model.toJSON();
            json.village = json.village.id;
            json.person_group = json.person_group.id;
            Backbone.Syphon.deserialize(this, json);
        },
        save: function() {
            // read data from form
            // check internet conn
            //if internect conn create on online coll/model(?) then create on offline coll/model        (server log ?)
            //else create on offline coll/model, add to local log
            var data = Backbone.Syphon.serialize(this);
            if (data.hasOwnProperty('')) {
                delete data[''];
            }
            
            var village = this.villages.where({
                id: data['village']
            })[0];
            var persongroup = this.persongroups.where({
                id: data['person_group']
            })[0];
            
            if(village)
            {
                data['village'] = {
                    'id': village.get('id'),
                    'village_name': village.get('village_name')
                };
            }
            else
                data['village']= {
                    'id': null,
                    'village_name': null
                };
            if(persongroup)
            {
                data['person_group'] = {
                    'id': persongroup.get('id'),
                    'group_name': persongroup.get('group_name')
                };
            }
            else
                data['person_group'] = {
                    'id':null,
                    'group_name': null
                };
                
            if (model) {
                model.set(data);
                console.log("editing person to:");
                console.log(JSON.stringify(model));
                model.save();

            } 
            else {
                this.person_offline_model.set(data);
                console.log("adding new person:");
                console.log(JSON.stringify(this.person_offline_model));
                this.person_offline_model.save();
            }

            appRouter.navigate('person', true);

        },

        render: function() {
            $(this.el)
                .html(this.add_edit_template());
            var curobj = this;    
            this.$('form').validate({submitHandler: function() { 
            curobj.save();
        }});
            
            return this;
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
                Backbone.Syphon.deserialize(this, json);
            }
        }

    });

    
    var HeaderView = Backbone.View.extend({
        events: {

        },

        template: _.template($('#header')
            .html()),

        render: function() {
            $(this.el)
                .html(this.template({
                }));
            return this;
        }
    });

    // Dashboard View - contains upload, download buttons, the list of models
    var DashboardView = Backbone.View.extend({
        events: {
            "click button#download": "Download",
        },
        fetch_save: function(collection_online, collection_offline, storeName) {
            var prevTime, curTime;
            curTime = (new Date())
                .getTime();
            prevTime = curTime;
            console.log("downloading  " + storeName);
            collection_online.fetch({
                success: function() {
                    data = (collection_online.toJSON());
                    console.log(storeName + " collection fetched ");
                    curTime = (new Date())
                        .getTime();
                    deltaTime = curTime - prevTime;
                    var download_time = deltaTime;
                    prevTime = curTime;
                    var db;
                    var request = indexedDB.open("coco-database");
                    request.onerror = function(event) {
                        console.log("Why didn't you allow my web app to use IndexedDB?!");
                    };
                    request.onsuccess = function(event) {
                        db = request.result;
                        var clearTransaction = db.transaction([storeName], "readwrite");
                        var clearRequest = clearTransaction.objectStore(storeName)
                            .clear();
                        clearRequest.onsuccess = function(event) {
                            console.log(storeName + ' objectstore cleared');
                            for (var i = 0; i < data.length; i++) {
                                collection_offline.create(data[i]);
                            }

                            curTime = (new Date())
                                .getTime();

                            deltaTime = curTime - prevTime;
                            var writing_time = deltaTime;
                            console.log(storeName + " downloaded");
                            console.log(storeName + " downlaod time = " + download_time);
                            console.log(storeName + " writing time = " + writing_time);

                        };



                    }
                }
            });
        },
        Download: function() {
            console.log("starting download");
            //Download:fetch each model from server and save it to the indexeddb
            
            villages_online = new village_online_collection();
            villages_offline = new village_offline_collection();
            this.fetch_save(villages_online, villages_offline, "village");

            videos_online = new video_online_collection();
            videos_offline = new video_offline_collection();
            this.fetch_save(videos_online, videos_offline, "video");

            persongroups_online = new persongroup_online_collection();
            persongroups_offline = new persongroup_offline_collection();
            this.fetch_save(persongroups_online, persongroups_offline, "persongroup");

            screenings_online = new screening_online_collection();
            screenings_offline = new screening_offline_collection();
            this.fetch_save(screenings_online, screenings_offline, "screening");

            persons_online = new person_online_collection();
            persons_offline = new person_offline_collection();
            this.fetch_save(persons_online, persons_offline, "person");

            personadoptvideos_online = new personadoptvideo_online_collection();
            personadoptvideos_offline = new personadoptvideo_offline_collection();
            this.fetch_save(personadoptvideos_online, personadoptvideos_offline, "personadoptvideo");

            animators_online = new animator_online_collection();
            animators_offline = new animator_offline_collection();
            this.fetch_save(animators_online, animators_offline, "animator");
        },
        template: _.template($('#dashboard')
            .html()),
        render: function() {
            $(this.el)
                .html(this.template({
            }));
            return this;
        }
    });

    var AppView = Backbone.View.extend({
        el: '#app',
        initialize: function() {
            header = new HeaderView();
            dashboard = new DashboardView({
                app: this
            });
            curr_list_view = null;
            current_add_edit_view = null;
            
        },
        render_dashboard: function() {
            $(this.el)
                .html('');
            $(this.el)
                .append(header.render('Dashboard')
                .el);
            $(this.el)
                .append(dashboard.render()
                .el);
            return this;

        },
        render_list_view: function(view_configs) {
            $(this.el)
                .html('');
            $(this.el)
                .append(header.render()
                .el);
            curr_list_view = new list_view(view_configs);
            $(this.el)
                .append(curr_list_view.render(view_configs.page_header)
                .el);
            return this;
        },
        render_add_edit_view: function(view_configs, data) {
            console.log("list view seen in add: ");
            console.log(curr_list_view);
            if (curr_list_view) {
                // ToDO: destroy this view, right now just turning off events for its collection 
                curr_list_view.collection.off();
            }
            $(this.el)
                .html('');
            $(this.el)
                .append(header.render("Add/Edit " + view_configs.page_header)
                .el);
            if (view_configs.page_header == "Persons") {
                current_add_edit_view = person_add_edit_view;
            } else {
                console.log("not person");
                return this;
            }
            $(this.el)
                .append(new current_add_edit_view({
                view_configs: view_configs,
                model_id: data
            })
                .render()
                .el);
            return this;
        }





    });


    var app = new AppView;

    var AppRouter = Backbone.Router.extend({
        routes: {
            "": "showDashboard",
            "person": "listPerson",
            "person/add": "addPerson",
            "person/edit/:id": "editPerson"
        },
        showDashboard: function() {
            console.log("showdashboard url caught");
            app.render_dashboard();
        },
        listPerson: function() {
            console.log("list person url caught");
            app.render_list_view(person_list_view_configs);
        },
        addPerson: function() {
            console.log("add person url caught");
            app.render_add_edit_view(person_list_view_configs, null);
        },
        editPerson: function(id) {
            console.log("edit person url caught, id = " + id);
            app.render_add_edit_view(person_list_view_configs, id);
        },

    });

    var appRouter = new AppRouter();
    Backbone.history.start();



});