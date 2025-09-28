conn = new Mongo();
db = conn.getDB("venus-sample-mongodb-ds");
let res = [
    db.venusPeopleDataModel.drop(),
    db.photobook_images.drop(),
    db.venusPeopleDataModel.insert({
        "id":"people-sample-001",
        "docTitle":"Fugerit Venus Doc Mongo DB Data Source Doc Title",
        "listPeople": [
            { "name":"Luthien", "surname":"Tinuviel", "title":"Queen" },
            { "name":"Thorin", "surname":"Oakshield", "title":"King" },
            { "name":"Gandalf", "surname":"The Grey", "title":"Wizard" }
        ]
    })
]
printjson(res)
