class FileSystem {

    Dir root;

    public FileSystem() {
        this.root = new Dir();
    }
    
    public List<String> ls(String path) {
        // example "/a/b"
        //we want to get to 'a', to check if b is a dir or file
        Dir t = root;
        List<String> content = new LinkedList<>();
        if (!path.equals("/")){
            //note that "/a/b" -> path.split("/") -> ["","a","b"]
            String[] d = path.split("/");
            //the first item is an empty stirng, so we start from index1
            for (int i=1;i<d.length-1;i++){
                t = t.dirs.get(d[i]);
            }
            if (t.files.containsKey(d[d.length-1])){
                content.add(d[d.length-1]);
                return content;
            }else{
                t = t.dirs.get(d[d.length-1]);
            }
        }
        //new emthod: addAll()
        content.addAll(new ArrayList<>(t.dirs.keySet()));
        content.addAll(new ArrayList<>(t.files.keySet()));
        Collections.sort(content);
        return content;
    }
    
    public void mkdir(String path) {
        //no need for explicitly check for if path.equals("/"), because it means to create the root directory, which already exists. so if that's the case, the code will not enter the for loop
        Dir t = root;
        String[] d = path.split("/");
        //"/a/b" gonna create the last item as the newest dir, so no need to stop before the last step
        for (int i =1; i<d.length;i++){
            if (!t.dirs.containsKey(d[i])){
                t.dirs.put(d[i], new Dir());
            }
            t = t.dirs.get(d[i]);
        }

    }
    
    public void addContentToFile(String filePath, String content) {
        Dir t = root;
        String[]d = filePath.split("/");
        //need to check if that file exists, so need to stop before the last level
        for (int i=1; i<d.length-1;i++){
            t = t.dirs.get(d[i]);
        }
        t.files.put(d[d.length-1], t.files.getOrDefault(d[d.length-1],"") + content);
    }
    
    public String readContentFromFile(String filePath) {
        Dir t = root;
        String[] d = filePath.split("/");
        //need to read the file, so we need to go the the second last level
        //"/a/b",we wanna go to "a"'s files map
        for (int i=1; i<d.length-1;i++){
            t = t.dirs.get(d[i]);
        }
        return t.files.get(d[d.length-1]);
    }
}

class Dir{
    Map<String, Dir> dirs;
    Map<String, String> files;

    public Dir(){
        this.dirs = new HashMap<>();
        this.files= new HashMap<>();
    }
}

/**
 * Your FileSystem object will be instantiated and called as such:
 * FileSystem obj = new FileSystem();
 * List<String> param_1 = obj.ls(path);
 * obj.mkdir(path);
 * obj.addContentToFile(filePath,content);
 * String param_4 = obj.readContentFromFile(filePath);
 */