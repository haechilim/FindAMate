package com.example.findamate.manager;

import android.os.AsyncTask;

import com.example.findamate.domain.School;
import com.example.findamate.domain.Student;
import com.example.findamate.helper.Logger;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ApiManager {
    private static final String HOST = "http://35.247.50.32:8000";
    private static int memberId;
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void getSchool(SchoolCallback callback) {
        request(String.format("%s/%s?memberId=%d", HOST, "school", memberId), new JsonCallback() {
            @Override
            public void success(String json) {
                try {
                    callback.success(objectMapper.readValue(json, new TypeReference<School>() {}));
                } catch (JsonProcessingException e) {
                    Logger.debug(e.getMessage());
                }
            }
        });
    }

    public static void updateSchool(School school) {
        request(String.format("%s/%s?memberId=%d&name=%s&year=%s&number=%s", HOST, "school/update", memberId,
                school.getName(), school.getYear(), school.getNumber()), new JsonCallback() {
            @Override
            public void success(String json) {
                Logger.debug(json);
            }
        });
    }

    public static void getStudents(StudentCallback callback) {
        request(String.format("%s/%s?memberId=%d", HOST, "student", memberId), new JsonCallback() {
            @Override
            public void success(String json) {
                try {
                    callback.success(objectMapper.readValue(json, new TypeReference<List<Student>>() {}));
                } catch (JsonProcessingException e) {
                    Logger.debug(e.getMessage());
                }
            }
        });
    }

    public static void addStudent(Student student, AMDStudentCallback callback) {
        request(String.format("%s/%s?memberId=%d&name=%s&male=%s&phone=%s&avatarId=%d&score=%d&happiness=%d&message=%s",
                HOST, "student/add", memberId, student.getName(), (student.isMale() ? "true" : "false"), student.getPhone(),
                student.getAvatarId(), student.getScore(), ((int)student.getHappiness()), student.getStatusMessage()), new JsonCallback() {
            @Override
            public void success(String json) {
                try {
                    callback.success(objectMapper.readValue(json, new TypeReference<Student>() {}));
                } catch (JsonProcessingException e) {
                    Logger.debug(e.getMessage());
                }
            }
        });
    }

    public static void modifyStudent(Student student, AMDStudentCallback callback) {
        request(String.format("%s/%s?id=%d&name=%s&male=%s&phone=%s&avatarId=%d&score=%d&happiness=%d&statusMessage=%s",
                HOST, "student/modify", student.getId(), student.getName(), (student.isMale() ? "true" : "false"), student.getPhone(),
                student.getAvatarId(), student.getScore(), ((int)student.getHappiness()), student.getStatusMessage()), new JsonCallback() {
            @Override
            public void success(String json) {
                try {
                    callback.success(objectMapper.readValue(json, new TypeReference<Student>() {}));
                } catch (JsonProcessingException e) {
                    Logger.debug(e.getMessage());
                }
            }
        });
    }

    private static void request(String url, JsonCallback callback) {
        new AsyncTask<String, Void, String>() {
            @Override
            protected String doInBackground(String... strings) {
                return request(strings[0]);
            }

            @Override
            protected void onPostExecute(String result) {
                callback.success(result);
            }
        }.execute(url);
    }

    private static String request(String uri) {
        String result = "";

        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            while(true) {
                String line = reader.readLine();
                if(line == null) break;
                result += line;
            }

            reader.close();
            connection.disconnect();
        } catch (MalformedURLException e) {
            Logger.debug(e.getMessage());
        } catch (IOException e) {
            Logger.debug(e.getMessage());
        }

        return result;
    }

    public static int getMemberId() {
        return memberId;
    }

    public static void setMemberId(int memberId) {
        ApiManager.memberId = memberId;
    }

    private interface JsonCallback {
        void success(String json);
    }

    public interface SchoolCallback {
        void success(School school);
    }

    public interface StudentCallback {
        void success(List<Student> students);
    }

    public interface AMDStudentCallback {
        void success(Student student);
    }
}