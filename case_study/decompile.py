from krakatau import decompiler

# Decompile a single class file
decompiled_code = decompiler.decompile_class_file("Example.class")
print(decompiled_code)

# Decompile a JAR file
# decompiler.decompile_jar_file("my_app.jar", output_directory="decompiled_src")