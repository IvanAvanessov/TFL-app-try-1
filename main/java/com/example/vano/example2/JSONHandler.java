package com.example.vano.example2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONHandler {

    String fieldName;
    JSONObject[] output;

    public JSONObject[] sortJsonArray(JSONArray input, String fn){

        int items = input.length();
        output = new JSONObject[items];
        for(int i = 0; i < items; i ++){
            try {
                output[i] = input.getJSONObject(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        fieldName = fn;
        sort(output, 0, items-1);
        return output;

    }
    void merge(JSONObject[] arr, int l, int m, int r)
    {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        JSONObject[] L = new JSONObject[n1];
        JSONObject[] R = new JSONObject[n2];

        /*Copy data to temp arrays*/
        for (int i=0; i<n1; ++i)
            L[i] = arr[l+i];
                    //[i] = arr[l + i];
        for (int j=0; j<n2; ++j)
            R[j] = arr[m + 1+ j];


        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarry array
        int k = l;
        while (i < n1 && j < n2)
        {
            try {
                if (Integer.parseInt(L[i].get(fieldName).toString()) <= Integer.parseInt(R[j].get(fieldName).toString()))
                {
                    arr[k] = L[i];
                    i++;
                }
                else
                {
                    arr[k] = R[j];
                    j++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1)
        {
            arr[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2)
        {
            arr[k] = R[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
    // merge()
    void sort(JSONObject[] arr, int l, int r)
    {
        if (l < r)
        {
            // Find the middle point
            int m = (l+r)/2;

            // Sort first and second halves
            sort(arr, l, m);
            sort(arr , m+1, r);

            // Merge the sorted halves
            merge(arr, l, m, r);
        }
    }

}
