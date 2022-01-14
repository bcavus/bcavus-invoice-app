mongo -- "$MONGO_INITDB_DATABASE" <<EOF
    var root_user = '$MONGO_INITDB_ROOT_USERNAME';
    var root_pass = '$MONGO_INITDB_ROOT_PASSWORD';
    var db = db.getSiblingDB('$MONGO_INITDB_DATABASE');
    db.auth(root_user, root_pass);

    var service_user = '$INVOICE_SERVICE_MONGO_DATABASE_USER';
    var service_pass = '$INVOICE_SERVICE_MONGO_DATABASE_PASSWORD';
    db.createUser({user: service_user, pwd: service_pass, roles: [{ role: "readWrite", db: '$INVOICE_SERVICE_MONGO_DATABASE_NAME' }]});
EOF